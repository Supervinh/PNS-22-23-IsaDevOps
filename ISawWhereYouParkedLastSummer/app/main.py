import httpx
from datetime import datetime
from fastapi import FastAPI, HTTPException
from fastapi_utils.tasks import repeat_every

numberplates = {}
app = FastAPI()


@app.post("/parking/{numberplate}", status_code=201)
async def park(numberplate: str):
    if numberplate in numberplates:
        raise HTTPException(status_code=409)
    else:
        print("Checking parked cars")
        numberplates[numberplate] = datetime.now()
        httpx.get("http://server:8080/payoff/notify")#,data={"numberplate": numberplate, "message": "Your free time has expired"})
        return {"message": "You are parked"}


@repeat_every(seconds=60)
def check_parked_cars():
    httpx.post("http://server:8080/payoff/notify",
               data={"numberplate": "abc", "message": "Your free time has expired"})
    print("Checking parked cars")
    for numberplate in numberplates:
        duration = numberplate - datetime.now()
        print(f"Duration for {numberplate} is {duration}")
        if duration.minutes == 2:
            httpx.post("http://server:8080/payoff/notify",
                       data={"numberplate": numberplate, "message": "Your free time has expired"})
            numberplates.pop(numberplate)
        elif duration.minutes == 1:
            httpx.post("http://server:8080/payoff/notify",
                       data={"numberplate": numberplate, "duration": "Only five minutes left"})
