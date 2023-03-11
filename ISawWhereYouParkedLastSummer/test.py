from datetime import datetime, timedelta
from time import sleep

numberplates = []
numberplate = "AGC"
numberplates.append({"mat": numberplate, "initialTime": datetime.now()})

print("post", numberplates)
boolA = True
while boolA:
    sleep(1)
    for numberplate in numberplates:
        delay = datetime.now() - numberplate["initialTime"]
        print(f"\tDuration for {numberplate['mat']} is {delay}")
        if 20 <= delay.seconds <= 360:
            numberplates.remove(numberplate)
        if 600 <= delay.seconds:
            print("You're done")
            boolA = False

# from datetime import datetime
#
# numberplates = []
# numberplate = "AGC"
# numberplates[numberplate] = datetime.now()
#
# print("post", numberplates)
#
# for numberplate in numberplates:
#     duration = numberplate - datetime.now()
#     print(f"Duration for {numberplate} is {duration}")
