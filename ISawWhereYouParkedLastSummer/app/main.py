import httpx
from datetime import datetime
from fastapi import FastAPI, HTTPException
from fastapi_utils.tasks import repeat_every

numberplates = []
app = FastAPI()


@app.post("/parking/{numberplate}", status_code=201)
async def park(numberplate: str):
    if numberplate in numberplates:
        raise HTTPException(status_code=409)
    else:
        numberplates.append({"mat": numberplate, "initialTime": datetime.now()})
        print("post", numberplates)
        return "Received your car's numberplate."


@app.on_event("startup")
@repeat_every(seconds=5)
def check_parked_cars():
    #     httpx.post("http://server:8080/payoff/notify",
    #                data={"numberplate": "abc", "message": "Your free time has expired"})
    print("Checking parked cars loop",numberplates)
    for numberplate in numberplates:
        delay = datetime.now() - numberplate["initialTime"]
        print(f"\tDuration for {numberplate['mat']} is {delay}",delay.seconds)
        print(5 <= delay.seconds <= 10)
        if 5 <= delay.seconds <= 10:
            data={"numberplate": numberplate['mat'], "message": "Only five minutes left"}
            print("Request",httpx.post("http://server:8080/payoff/notify",json=data))
            print("Sending notification")
        if 15 <= delay.seconds:
            print("Sending notification & removing car")
            data={"numberplate": numberplate['mat'], "message": "Your free time has expired"}
            httpx.post("http://server:8080/payoff/notify",json = data)
            numberplates.remove(numberplate)

#     for numberplate in numberplates:
#         duration = numberplate - datetime.now()
#         print(f"Duration for {numberplate} is {duration}")
#         if duration.minutes == 2:
#             httpx.post("http://server:8080/payoff/notify",
#                        data={"numberplate": numberplate, "message": "Your free time has expired"})
#             numberplates.pop(numberplate)
#         elif duration.minutes == 1:
#             httpx.post("http://server:8080/payoff/notify",
#                        data={"numberplate": numberplate, "duration": "Only five minutes left"})
