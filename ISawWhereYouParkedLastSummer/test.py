from datetime import datetime
from time import sleep

numberplates = {}
numberplate = "AGC"
numberplates[numberplate] = datetime.now()
numberplate = "XYZ"
numberplates[numberplate] = datetime.now()
# .append({"mat": numberplate, "initialTime": datetime.now()})

print("post", numberplates)
boolA = True
while boolA:
    sleep(1)
    remove = []
    for numberplate, initTime in numberplates.items():
        print(numberplate, initTime)
        delay = datetime.now() - initTime
        print(f"\tDuration for {numberplate} is {delay}")
        if 5 <= delay.seconds <= 360:
            remove.append(numberplate)
        if 600 <= delay.seconds:
            print("You're done")
            boolA = False
    for i in remove:
        numberplates.pop(i)

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
