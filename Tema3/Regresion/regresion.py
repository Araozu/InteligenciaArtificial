import matplotlib.pyplot as plt
import numpy as np
import datetime
from sklearn.linear_model import LinearRegression
import csv
with open("./covid_19_data.csv") as csv_file:

    csv_reader = csv.reader(csv_file, delimiter=",")
    line_count = 0

    datos = dict()
    for row in csv_reader:
        if line_count == 0:
            print(f'Column names are {", ".join(row)}')
            line_count += 1
        else:
            strFecha = row[1].split("/")
            datetime_v = datetime.datetime(int(strFecha[2]), int(strFecha[0]), int(strFecha[1]))
            numDias = round(datetime_v.timestamp() / 60 / 60 / 24) - 18282

            if datos.get(numDias) is not None:
                datos[numDias] += float(row[5])
            else:
                datos[numDias] = 0.0

    arr_x = []
    arr_y = []

    for k, v in datos.items():
        arr_x.append(float(k))
        arr_y.append(v)

    # 227 dias
    x = np.array(arr_x).reshape((-1, 1))
    y = np.array(arr_y)

    x_train = x[:-113]
    x_test = x[-114:]

    y_train = y[:-113]
    y_test = y[-114:]

    model = LinearRegression()
    model.fit(x, y)

    y_prediction = model.predict(x)

    r_sq = model.score(x, y)
    print('coefficient of determination:', r_sq)

    plt.scatter(x, y, color="black")
    plt.plot(x, y_prediction, color="blue", linewidth=3)

    plt.xlabel("Dia")
    plt.ylabel("Casos")
    plt.xticks(())
    plt.yticks(())

    plt.show()
