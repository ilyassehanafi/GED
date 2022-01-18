Ajouter ce code à votre base de donnée pour aggrandir les packets envoyé du site web à la base de donnée
SET GLOBAL max_allowed_packet=1073741824;
 
NB :  au niveau de application properties vous devez changer le user et le mot de passe de votre base de donnée et le url de la base de donnée ,
sinon il faut créer la base de donnée dans SQL avec le user : root , mot de passe : 3deeea1629 et url : 127.0.0.1:3306
