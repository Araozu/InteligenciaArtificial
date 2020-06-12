import matplotlib.pyplot as plt
import random
from math import sqrt, pow


def distancia(p1, p2):
    """Obtiene la distancia euclidanea entre 2 puntos."""
    x1 = p1[0]
    y1 = p1[1]
    x2 = p2[0]
    y2 = p2[1]
    return sqrt(pow(x2 - x1, 2) + pow(y2 - y1, 2))


def centroides_iguales(arr1, arr2):
    """Compara dos grupos de centroides."""
    i = 0
    while i < len(arr1):
        if arr1[i][0] != arr2[i][0] or arr1[i][1] != arr2[i][1]:
            return False
        i += 1

    return True


colores = ["red", "lawngreen", "darkorange", "darkgreen", "teal", "dodgerblue", "indigo"]

# Conjunto de puntos de la formo [x, y, num_cluster], donde num_cluster es el numero de
# cluster al cual se encuentran más cerca
puntos = [[random.randint(1, 100), random.randint(1, 100), 0] for _ in range(0, 100)]

num_clusters = 5


def clusterizar(puntos, num_clusters):

    centroides_res = [puntos[random.randint(0, len(puntos) - 1)] for _ in range(num_clusters)]
    centroides = [[0.0, 0.0, 0] for _ in range(num_clusters)]

    while not centroides_iguales(centroides, centroides_res):

        # Los centroides que se encuentran el el centro de los clusters.
        # Almacenan [x, y, num_elementos_del_cluster]
        centroides = centroides_res
        centroides_res = [[0.0, 0.0, 0] for _ in range(num_clusters)]

        for pos_punto, punto_actual in enumerate(puntos):

            pos_centroide_mas_cercano = 0
            centroide_mas_cercano = centroides[0]
            distancia_a_centroide_mas_cercano = distancia(punto_actual, centroide_mas_cercano)

            # Si se encuentra un centroide mas cercano al punto actual, actualizar.
            for pos_centroide, sig_centroide in enumerate(centroides[1:]):
                sig_distancia = distancia(punto_actual, sig_centroide)

                if sig_distancia < distancia_a_centroide_mas_cercano:
                    pos_centroide_mas_cercano = pos_centroide + 1
                    centroide_mas_cercano = sig_centroide
                    distancia_a_centroide_mas_cercano = sig_distancia

            # Actualizar los datos
            puntos[pos_punto][2] = pos_centroide_mas_cercano
            # Actualizar los datos del centroide resultado.
            centroides_res[pos_centroide_mas_cercano][0] += punto_actual[0]
            centroides_res[pos_centroide_mas_cercano][1] += punto_actual[1]
            centroides_res[pos_centroide_mas_cercano][2] += 1

        # Calcular la posicion promedio de los centroides resultado
        for pos_cen_res, cen_res in enumerate(centroides_res):
            centroides_res[pos_cen_res][0] = cen_res[0] / cen_res[2]
            centroides_res[pos_cen_res][1] = cen_res[1] / cen_res[2]

    # Graficar los puntos
    for pos_punto, punto in enumerate(puntos):
        color = colores[punto[2]]
        plt.scatter(punto[0], punto[1], color=color)

    # Graficar los centroides resultado
    for c in centroides_res:
        plt.scatter(c[0], c[1], color="black", marker="*")

    plt.xticks(())
    plt.yticks(())
    plt.show()


clusterizar(puntos, num_clusters)
