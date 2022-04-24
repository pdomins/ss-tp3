from cmath import sqrt
from os import times
import numpy as np
import math
from matplotlib import pyplot as plt
from statistics import mean 
import matplotlib.ticker as mticker

# Read input
file = open("DVsT.txt", 'r')
InputLines = file.readlines()

times0 = []
times1 = []
times2 = []
times3 = []
d = []

## 3500

for line in InputLines:
    str = line.strip().split(' ')
    times0.append(int(str[0]))
    times1.append(int(str[1]))
    times2.append(int(str[2]))
    times3.append(int(str[3]))
    d.append(float(str[4]))

aux = [times0[0],  times1[0] , times2[0] , times3[0]]
stdDev1 = np.std(aux)
aux2 = [times0[1],  times1[1] , times2[1] , times3[1]]
stdDev2 = np.std(aux2)
aux3 = [times0[2] , times1[2] , times2[2] , times3[2]]
stdDev3 = np.std(aux3)
aux4 = [times0[3] , times1[3] , times2[3] , times3[3]]
stdDev4 = np.std(aux4)
aux5 = [times0[4] , times1[4] , times2[4] , times3[4]]
stdDev5 = np.std(aux5)
aux6 = [times0[5] , times1[5] , times2[5] , times3[5]]
stdDev6 = np.std(aux6)

averageTime = [np.average(aux), np.average(aux2), np.average(aux3), np.average(aux4), np.average(aux5), np.average(aux6)]



fig, ax = plt.subplots()

ax.scatter(d, averageTime)

print(averageTime)
##y_error = 20*0.10             ## El 10% de error
y_error = [stdDev1, stdDev2, stdDev3, stdDev4, stdDev5, stdDev6]


plt.errorbar(d,averageTime, yerr = y_error, capsize = 3)
##ax.set_title("Tiempo de equilibrio en funcion de D con N=???")
ax.set_xlabel('Ancho de la rendija')
ax.set_ylabel('Tiempo (ms)')

plt.ticklabel_format(style='sci', axis='y', scilimits=(0,0))
ax.yaxis.major.formatter._useMathText = True
plt.show()
plt.close(fig)
