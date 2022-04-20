
from os import times
import math
import numpy as np
from matplotlib import pyplot as plt
from statistics import mean 

# Read input
file = open("NVsT.txt", 'r')
InputLines = file.readlines()

pos0 = []
pos1 = []
pos2 = []
pos3 = []
n = []

for line in InputLines:
    str = line.strip().split(' ')
    pos0.append(int(str[0]))
    pos1.append(int(str[1]))
    pos2.append(int(str[2]))
    pos3.append(int(str[3]))
    n.append(int(str[4]))


times0 = [pos0[0],  pos1[0] , pos2[0] , pos3[0]]
stdDev1 = np.std(times0)
times1 = [pos0[1],  pos1[1] , pos2[1] , pos3[1]]
stdDev2 = np.std(times1)
times2 = [pos0[2] , pos1[2] , pos2[2] , pos3[2]]
stdDev3 = np.std(times2)
times3 = [pos0[3] , pos1[3] , pos2[3] , pos3[3]]
stdDev4 = np.std(times3)
times4 = [pos0[4] , pos1[4] , pos2[4] , pos3[4]]
stdDev5 = np.std(times4)
times5 = [pos0[5] , pos1[5] , pos2[5] , pos3[5]]
stdDev6 = np.std(times5)
times6 = [pos0[6] , pos1[6] , pos2[6] , pos3[6]]
stdDev7 = np.std(times6)


averageTime = [np.average(times0), np.average(times1), np.average(times2), np.average(times3), np.average(times4), np.average(times5), np.average(times6)]
##print(n)
fig, ax = plt.subplots()
ax.scatter(n, averageTime)


##y_error = 20*0.10             ## El 10% de error
y_error = [stdDev1, stdDev2, stdDev3, stdDev4, stdDev5, stdDev6, stdDev7]


plt.errorbar(n,averageTime, yerr = y_error, capsize = 3)
##ax.set_title("Tiempo de equilibrio en funcion de N con D=??")
ax.set_xlabel('Cantidad de particulas')
ax.set_ylabel('Tiempo (s)')


plt.show()
plt.close(fig)
