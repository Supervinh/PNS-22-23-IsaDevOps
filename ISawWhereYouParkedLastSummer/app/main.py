from datetime import datetime

import httpx as httpx
from fastapi import FastAPI, HTTPException
from fastapi_utils.tasks import repeat_every

numberplates = {}
app = FastAPI()

@app.post("/parking/{numberplate}", status_code=201)
async def park(numberplate: str):
    if (numberplate in numberplates):
        raise HTTPException(status_code=409)
    else:
        numberplates[numberplate] = datetime.now()
        return {"message": "You are parked"}


@repeat_every(seconds=60)
def check_parked_cars():
    for numberplate in numberplates:
        duration = numberplate - datetime.now()
        if (duration.minutes == 30):
            httpx.post("http://localhost:8080/notify",
                       data={"numberplate": numberplate, "message": "Your free time has expired"})
            numberplates.pop(numberplate)
        elif (duration.minutes == 1):
            httpx.post("http://localhost:8080/notify",
                       data={"numberplate": numberplate, "duration": "Only five minutes left"})
