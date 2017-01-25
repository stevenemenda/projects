# -*- coding: utf-8 -*-
"""
Created on Thu Oct  2 16:49:12 2014

@author: salmon
"""

import numpy as np
import matplotlib.pyplot as plt
from matplotlib import rc
import seaborn as sns
from os import mkdir, path
from sklearn.datasets import make_blobs
from sklearn.preprocessing import StandardScaler
from sklearn.decomposition import PCA
from math import cos, sin, pi, acos

# Uncomment the following 2 lines for Mac OS X / Spyder for using Tex display
# import os as macosx
# macosx.environ['PATH'] = macosx.environ['PATH'] + ':/usr/texbin'

###############################################################################
# Plot initialization

dirname = "../prebuiltimages/"
if not path.exists(dirname):
    mkdir(dirname)

imageformat = '.pdf'
rc('font', **{'family': 'sans-serif', 'sans-serif': ['Computer Modern Roman']})
params = {'axes.labelsize': 16,
          'font.size': 23,
          'legend.fontsize': 12,
          'xtick.labelsize': 10,
          'ytick.labelsize': 10,
          'text.usetex': True
          }
plt.rcParams.update(params)
plt.close("all")


def my_saving_display(fig, dirname, filename, imageformat):
    """"saving faster"""
    dirname + filename + imageformat
    image_name = dirname + filename + imageformat
    fig.savefig(image_name)


###############################################################################
# SVD/OLS (un)stability

n_features = 50
n_samples = 30
x = 10.**(-np.arange(n_samples,))
X = (np.column_stack([x**(i) for i in range(n_features)]))
U, s, V = np.linalg.svd(X, full_matrices=False)
theta_true = np.zeros([n_features, ])
theta_true[0:5] = 1
theta_true = theta_true + 0.2 * np.random.rand(n_features,)

y_true = np.dot(X, theta_true)
err = np.zeros(n_samples,)
err_delta = np.zeros(n_samples,)
for i in range(1, n_samples):
    delta = 10.**(-i) * (0.5 - np.random.rand(n_samples,)) * y_true
    y = y_true + delta
    w = np.dot(np.transpose(U), y)
    theta_hat = np.dot(np.transpose(V), w / s)
    err[i] = np.sqrt(np.sum((theta_hat - theta_true)**2))
    err_delta[i] = np.sqrt(np.sum(delta**2))

# sns.set_context("poster")
# sns.set_style("ticks")
fig1 = plt.figure(figsize=(12, 8))
ax1 = fig1.add_subplot(111)
ax1.plot(err_delta, err, '*', markersize=20)
ax1.set_yscale('log')
ax1.set_xscale('log')
sns.despine()
ax1.set_xlabel(r"$\|\Delta\|_2$")
ax1.set_ylabel(r"$\|\theta^{\Delta}-\hat\theta|_2$")
plt.title(r"$s_1={0:.2e}, s_2={1:.2e}, s_3={2:.2e}, s_4={3:.2e}, s_5={4:.2e},s_6={5:.2e}$".format(s[0],s[1],s[2],s[3],s[4],s[5]),
          fontsize=16)

my_saving_display(fig1, dirname, "amplification_erreur", imageformat)

###############################################################################
# PCA interpretation

color_blind_list = sns.color_palette("colorblind", 8)

centers = [(-5, -5), (0, 0), (5, 5)]
Z, _ = make_blobs(n_samples=50, n_features=2, cluster_std=4.0,
                  centers=centers, shuffle=False, random_state=42)

scaler = StandardScaler(with_mean=True, with_std=True).fit(Z)
X = scaler.transform(Z)


my_orange = color_blind_list[2]
my_green = color_blind_list[1]

sns.set_context("poster")
sns.set_palette("colorblind")
sns.axes_style()
sns.set_style("white")

# Data scatterplot

fig = plt.figure(00)
sub1 = fig.add_subplot(121)
sub1.scatter(X[:, 0], X[:, 1], s=98, alpha=1, c=my_orange, zorder=1)
sub1.get_yaxis().set_ticks([])
sub1.get_xaxis().set_ticks([])
sub1.set_ylim([-3., 3.])
sub1.set_xlim([-3., 3.])
sub1.set_aspect('equal')
sub1.set_xlabel(u'Data')
plt.show()
filename = 'fig_pca_axis_raw'
my_saving_display(fig, dirname, filename, imageformat)

# with mean
sub1.scatter(0, 0, s=300, alpha=1, c='r', zorder=2)
plt.show()
sub1.set_aspect('equal')
sub1.set_xlabel(u'Data and mean')
filename = 'fig_pca_axis_raw_mean'
my_saving_display(fig, dirname, filename, imageformat)

x_range = np.linspace(-2, 2, num=100)
X_mean = X.mean(axis=0)

thetas = np.linspace(- pi / 2, pi / 2, num=10)
theta_grid = np.linspace(- pi / 2 - 0.2, pi / 2 + 0.2, num=200)
var_grid = np.zeros(theta_grid.shape)

for image_nb, theta in enumerate(theta_grid):
    rotation = np.asarray([cos(theta), sin(theta)])
    var_grid[image_nb] = np.var(X.dot(rotation))

for image_nb, theta in enumerate(thetas):
    fig = plt.figure(image_nb)
    sub1 = fig.add_subplot(121)
    sub1.set_aspect('equal')
    sub1.set_ylim([-3., 3.])
    sub1.set_xlim([-3., 3.])
    cos_var = np.asarray([cos(theta), -cos(theta)])
    sin_var = np.asarray([sin(theta), -sin(theta)])
    rotation = np.asarray([cos(theta), sin(theta)])
    points_projected = np.outer(X.dot(rotation), rotation)

    for i in range(X.shape[0]):
        point_projected = points_projected[i]
        point_ini = X[i, :]
        sub1.plot([point_ini[0], point_projected[0]],
                  [point_ini[1], point_projected[1]],
                  '--k', linewidth=1, zorder=1)
        sub1.scatter(point_projected[0], point_projected[1],
                     s=100, alpha=1, c='k', zorder=2)
        amplitude = 4
        sub1.plot(amplitude * cos_var, amplitude * sin_var, 'k', linewidth=2,
                  zorder=2)
        # plt.xlim(-2, 2)
        # plt.ylim(-2, 2)

    sub1.scatter(X[:, 0], X[:, 1], s=98, alpha=1, c=my_orange, zorder=3)
    sub1.scatter(0, 0, s=300, alpha=1, c='r', zorder=4)
    sub1.get_yaxis().set_ticks([])
    sub1.get_xaxis().set_ticks([])
    sub1.set_xlabel(u'Data, mean and projection')
    plt.show()
    sub2 = fig.add_subplot(122)
    var = np.var(X.dot(rotation))
    sub2.plot(theta_grid, var_grid, c='k', linewidth=5, zorder=1)
    sub2.set_aspect('equal')
    sub2.set_ylim([0, 2.])
    sub2.set_xlim([- pi / 2 - 0.2, pi / 2 + 0.2])
    sub2.scatter(theta, var, c=my_orange, s=300, zorder=2)
    sub2.set_xlabel('Angle')
    sub2.set_ylabel('Variance')
    sub2.get_yaxis().set_ticks([])
    sub2.get_xaxis().set_ticks([])
    plt.show()
    filename = 'fig_pca_axis' + str(image_nb)
    my_saving_display(fig, dirname, filename, imageformat)
plt.close('all')


# PCA itself

pca = PCA(n_components=2)
pca.fit(X)
rotation = np.asarray([pca.components_[0][0], pca.components_[0][0]])
X_new = np.outer(X.dot(rotation), rotation)
# X_new = np.outer(pca.transform(X), pca.components_[0])
theta_opt = acos(pca.components_[0][0])

fig = plt.figure(3)
sub1 = fig.add_subplot(121)
sub1.scatter(X[:, 0], X[:, 1], s=98, alpha=1, c=my_orange, zorder=3)
sub1.get_yaxis().set_ticks([])
sub1.get_xaxis().set_ticks([])
sub1.set_ylim([-3., 3.])
sub1.set_xlim([-3., 3.])
sub1.set_xlabel(u'Principal direction (main axis)')
sub1.set_aspect('equal')

for i in range(X.shape[0]):
    point_projected = X_new[i]
    point_ini = X[i, :]
    sub1.plot([point_ini[0], point_projected[0]],
              [point_ini[1], point_projected[1]],
              '--k', linewidth=1, zorder=2)
    sub1.scatter(point_projected[0], point_projected[1],
                 s=100, alpha=1, c='k', zorder=3)
    amplitude = 4
    cos_var = np.asarray([pca.components_[0][0], -pca.components_[0][0]])
    sin_var = np.asarray([pca.components_[0][1], -pca.components_[0][0]])
    sub1.plot(amplitude * cos_var, amplitude * sin_var, 'k', linewidth=2,
              zorder=2)


sub1.scatter(0, 0, s=300, alpha=1, c='r', zorder=3)
var = pca.explained_variance_[0]
sub2 = fig.add_subplot(122)
sub2.plot(theta_grid, var_grid, c='k', linewidth=5, zorder=1)
sub2.set_aspect('equal')
sub2.set_ylim([0, 2.])
sub2.set_xlim([- pi / 2 - 0.2, pi / 2 + 0.2])
sub2.scatter(theta_opt, var, c='r', s=300, zorder=2)
sub2.set_xlabel('Angle')
sub2.set_ylabel('Variance')
sub2.get_yaxis().set_ticks([])
sub2.get_xaxis().set_ticks([])
filename = 'fig_pca_axis_opt'
my_saving_display(fig, dirname, filename, imageformat)
