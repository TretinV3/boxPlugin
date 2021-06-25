# Présentation :
Voici mon premier plugin, il est fait pour créer des casino/box dans minecraft grace a des clés.

Images :

![gui du casino](https://i.ibb.co/ZG4P89p/2021-06-25-11-52-29.png)

![configuration](https://i.ibb.co/Sttzk3h/Capture-d-e-cran-2021-06-25-a-11-56-18.png)

# Configuration :

Pour créer une box (et donc une clé associé) tous ce passe dans le fichier de configuration :
```yml
box:
  vip:
    - '{"type":"DIAMOND_BLOCK","amount":1}'
    - '{"type":"DRAGON_EGG",toto"amount":6}'
    - '{"type":"DIAMOND_BLOCK","amount":64,"display":true}'
```
Ici, on définit les objets que l'on peux gagner avec une clé *vip*, vous pouvez donc créer autant de clé que vous voulez !
Si vous avez un item spécial que vous voulez rajouter, vous pouvez faire la commande `/jsonitem` pour récuperer son json. Pour afficher le message définit dans `say.broadcastWhenWin` il faut rajouter `"display":true` dans le json.
