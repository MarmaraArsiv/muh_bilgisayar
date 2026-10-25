import numpy as np
import matplotlib.pyplot as plt

#define accelerations and kinetic friction coefficients data
angles = np.array([30, 30, 30, 45, 45, 45, 60, 60, 60])
ranges = np.array([350, 420, 410, 480, 485, 486, 390, 430, 425])

#find fit line
m, n = np.polyfit(angles, ranges, 1)

#add points to plot
plt.scatter(angles, ranges, color='steelblue')

#add fit line to plot
plt.plot(angles, m*angles+n, color='red', linewidth=2, label= 'Fit line: ' + 'f(x) = ' + '{:.3f}'.format(m)  + 'x' + ' + {:.3f}'.format(n))

plt.xlabel('Angles')
plt.ylabel('Range')
plt.title('Fit Line of Steel Ball at First Level')

plt.legend()
plt.show()