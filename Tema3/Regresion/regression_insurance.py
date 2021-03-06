import matplotlib.pyplot as plt
import numpy as np
from sklearn.linear_model import LinearRegression
import csv
with open("./insurance.csv") as csv_file:

    csv_reader = csv.reader(csv_file, delimiter=",")
    line_count = 0

    arr_x = []
    arr_y = []

    for row in csv_reader:
        if line_count == 0:
            line_count += 1
        else:
            edad = float(row[0])
            costo = float(row[2])
            arr_x.append(edad)
            arr_y.append(costo)

    x = np.array(arr_x).reshape((-1, 1))
    y = np.array(arr_y)

    x = x[:50]
    y = y[:50]

    model = LinearRegression()
    model.fit(x, y)

    y_prediction = model.predict(x)

    print(model.score(x, y))
    print(model.coef_)
    print(model.intercept_)

    plt.scatter(x, y, color="black")
    plt.plot(x, y_prediction, color="blue", linewidth=3)

    plt.xlabel("Edad")
    plt.ylabel("Costo")
    plt.xticks(())
    plt.yticks(())

    plt.show()
