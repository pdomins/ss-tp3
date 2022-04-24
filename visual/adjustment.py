from matplotlib import pyplot as plt
import numpy as np
pressures = [5.113825, 10.55905, 15.79635, 26.195349999999998, 29.29125]
temperatures = [0.00005, 0.0002, 0.00045, 0.0008, 0.00125]
initial_c = 20147


c_values = []
e_values = []

min_error = 800
min_c = 40000           #numeros que no alcanza nunca
for c in range(26800, 28000):
    c_values.append(c)
    error = 0
    for j in range(0, len(pressures)):
        error += (pressures[j] - c * temperatures[j])**2
    if error < min_error:
        min_error = error
        min_c = c
    e_values.append(error)
    print(error, " ", c, "\n")

print(min_error, " ", min_c)

fig, ax = plt.subplots()
ax.scatter(c_values, e_values)
ax.set_xlabel('c')
ax.set_ylabel('Error')

plt.show()
plt.close(fig)