# mini-projet
# auteur: Lieqiang GUO
# e-mail: lieqiang.guo@telecom-paristech.fr
# t??l: 33+0680766668


set.seed(42,kind="Marsaglia-Multicarry");

# Exercice:1

# etape: a
n <- 10; #taille de l'??chantillon
m <- 100000; # le nombre d'??chantillons M

theta_0 <- 0.2; # le vrai theta

g_theta= 1/theta_0;

h <- n/(n+1-theta_0); # h= h*(theta_0)

Z <- matrix(rgeom(n*m,theta_0),n,m);
Z <- Z + 1; # ~ Y+1

# etape: c

Tx <- t(apply(Z,2,'mean')); # vector colone
Sx <- h*Tx;

hist(Sx, col = "red", probability = TRUE, main = "EX1 :Histogrammes de Tx et Sx", breaks = 50,
     xlab = "Estimations Sx et Tx de g(theta)",
     ylab = "Probabilite")

hist(Tx, col = "blue", probability = TRUE, add = TRUE, density = 15, breaks = 50)

abline(v = 1/theta_0, lwd = 3)

legend("topright", lwd = 2, col = c("red", "blue", "black"),
       legend = c("Sx", "Tx", "g(theta)"))

# etape: d

L1= (Tx - mean(Tx))^2;
Lh= (Sx - mean(Tx))^2;

# etape: e

R_T_appro <- mean(L1);
R_S_appro <- mean(Lh);
R_T <- (1 - theta_0)/(n*theta_0^2);
R_S <- h^2*(1 - theta_0)/(n*theta_0^2) + ((h -1)/theta_0)^2;


# etape: f
dev.new() # un nouveau graphe

hist(L1, col = "red", probability = TRUE, main = "EX1 :Histogrammes de L1 et Lh", breaks = 50,
     xlab = "Estimations L1 et Lh",
     ylab = "Probabilite")

hist(Lh, col = "blue", probability = TRUE, add = TRUE, density = 15, breaks = 50)

abline(v = c(R_T,R_S), lwd = 3, col = c("green", "yellow"))

legend("topright", lwd = 2, col = c("red", "blue", "green", "yellow"),
       legend = c("L1", "Lh", "R(theta_0,T)", "R(theta_0,Sh)"))


# Exercice:2

theta_0 <- 0.6;

# etape: a

N <- 500;
X <- rgeom(N, theta_0) + 1;
grille <- seq(0, 1, by <- 0.01);
L <- length(grille);
grille <- grille[-c(1,L)]; # delete the first and the last elements in grille.

# etape: b
n_s <- c(5, 20, 100, 500);
a <- 1/2;
b <- 3/2;
col = c("red", "blue", "yellow", "green", "brown", "black");
densite_post <- c();
for (n in n_s){
  densite_post <- c(densite_post,dbeta(grille, a+n, b-n+sum(X[1:n])));
}

densite_pi <- dbeta(grille, a, b);
densite_post <- c(densite_post, densite_pi);
ylim <- range(min(densite_post),max(densite_post));
densite_Matrix <- t(matrix(densite_post,length(densite_pi),length(densite_post)/length(densite_pi)));
dev.new() # un nouveau graphe
plot(grille,densite_Matrix[1,],ylim = ylim, type = "l", col = col[1], ylab = "densité", xlab = "grille de theta");
title("EX2:")
for(i in (2:(length(densite_post)/length(densite_pi)))){
  lines(grille, densite_Matrix[i,], type = "l", col = col[i %% (length(col) + 1)]);
}

abline(v = theta_0, col ="black");

legend("topright", lwd = 1, col = col,
       legend = c("n = 5", "n = 20", "n = 100", "n = 500", "pi(theta)", "theta_0"));

# on peut regarder que les sommets de E_pi(theta|X^n) des differents n sont situés où le theta est equal 0.6. 
# donc on peut obtenir le vrai theta même si on le sais pas.

dev.new() # un nouveau graphe
n <- 1:N;
E_theta_x <- (a + n)/(a + b + cumsum(X));
plot(n, E_theta_x, type = "l", col = "red", ylab = "estimation de theta");
abline(h = theta_0, lwd = 3, col = "blue");
title("EX2:");
legend("topright", lwd = 1, col = c("red","blue"),
       legend = c("E_pi(theta|X^n)", "theta_0 = 0.6"));

# on peut regarder que quand n est plus grand, E_theta_x est plus proche du vrai theta. Le theta que le physicien croit et le vrai theta
# sont differents, mais on peut 

# Exercice: 3
#etape: a
a <- 5/100;
n <- c(10, 100, 1000);
quantille <- 1-a/2;
A_n <- qnorm(quantille)/sqrt(n);
t(matrix(c(n,A_n),3))
u <- 0.1;
n0 <- 0;
R_H1 <- c();
beta <- 0.95;
n <- seq(50,5000,200);
A_n <- qnorm(quantille)/sqrt(n);
for (i in n){
  temp_R_H1 <- pnorm(q = A_n[which(i==n)], mean = u, sd = sqrt(1/i)) - pnorm(q = - A_n[which(i==n)], mean = u, sd = sqrt(1/i));
  R_H1 <- c(R_H1, temp_R_H1);
}
n0 <- n[min(which((1 - R_H1) >= beta))];
n0
dev.new() # un nouveau graphe
plot(n, R_H1, type = 'l', xlab = "n", ylab = "risque R de H1", col = "red");
title("EX3:")
n <- seq(50,n0,1);
A_n <- qnorm(quantille)/sqrt(n);
R_H1 <- c();
for (i in n){
  temp_R_H1 <- pnorm(q = A_n[which(i==n)], mean = u, sd = sqrt(1/i)) - pnorm(q = - A_n[which(i==n)], mean = u, sd = sqrt(1/i));
  R_H1 <- c(R_H1, temp_R_H1);
}
n0 <- n[min(which((1 - R_H1) >= beta))];
n0