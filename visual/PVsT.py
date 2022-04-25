from cmath import sqrt
from os import times
import numpy as np
import math
from matplotlib import pyplot as plt
from statistics import mean 
import matplotlib.ticker as mticker

# Read input
file = open("PVsT.txt", 'r')
InputLines = file.readlines()

pressure0 = []
pressure1 = []
pressure2 = []
pressure3 = []
e = []

## 3500

for line in InputLines:
    str = line.strip().split(' ')
    pressure0.append(float(str[0]))
    pressure1.append(float(str[1]))
    pressure2.append(float(str[2]))
    pressure3.append(float(str[3]))
    e.append(float(str[4]))

aux = [pressure0[0],  pressure1[0] , pressure2[0] , pressure3[0]]
stdDev1 = np.std(aux)
aux2 = [pressure0[1],  pressure1[1] , pressure2[1] , pressure3[1]]
stdDev2 = np.std(aux2)
aux3 = [pressure0[2] , pressure1[2] , pressure2[2] , pressure3[2]]
stdDev3 = np.std(aux3)
aux4 = [pressure0[3] , pressure1[3] , pressure2[3] , pressure3[3]]
stdDev4 = np.std(aux4)


averageTime = [np.average(aux), np.average(aux2), np.average(aux3), np.average(aux4)]



fig, ax = plt.subplots()

ax.scatter(e, averageTime)

print(averageTime)
print(e)
##y_error = 20*0.10             ## El 10% de error
y_error = [stdDev1, stdDev2, stdDev3, stdDev4]

plt.xlim(0.00005 * 0, 0.0010 * 1)
plt.errorbar(e,averageTime, yerr = y_error, capsize = 3, elinewidth=1, markeredgewidth=1)
##ax.set_title("Tiempo de equilibrio en funcion de D con N=???")
ax.set_xlabel('Energia (J)')
ax.set_ylabel('Presion (N/m)')



plt.show()
plt.close(fig)
