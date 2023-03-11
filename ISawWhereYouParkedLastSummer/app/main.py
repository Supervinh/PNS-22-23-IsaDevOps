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
        numberplates[numberplate] = datetime.now()
        return "Received " + numberplate


@app.on_event("startup")
@repeat_every(seconds=60)
def check_parked_cars(): #Delay should be 25 and 30 minutes, there are reduced for demo purposes here
    remove = []
    for numberplate, initTime in numberplates.items():
        delay = datetime.now() - initTime
        if 5*60 <= delay.seconds:
            data = {"numberplate": numberplate, "message": "Your free time has expired"}
            httpx.post("http://server:8080/payoff/notify", json=data)
            remove.append(numberplate)
        elif 2*60 <= delay.seconds < 3*60:
            data = {"numberplate": numberplate, "message": "Only five minutes left"}
            httpx.post("http://server:8080/payoff/notify", json=data)
    for i in remove:
        numberplates.pop(i)
    remove.clear()
