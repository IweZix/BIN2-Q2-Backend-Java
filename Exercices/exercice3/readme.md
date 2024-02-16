| URI            | Méthode HTTP | Auths? | Opération                                                                  |
|----------------|--------------|--------|----------------------------------------------------------------------------|
| **pages**      | GET          | Non    | READ ALL : Lire toutes les ressources de la collection pages               |
| **pages/{id}** | GET          | Non    | READ ONE : Lire la ressource pages idenetifée                              |
| **films/{id}** | POST         | Non    | CREATE ONE : Créer une ressource pages basée sur les données de la requête |
| **films/{id}** | DELETE       | JWT    | DELETE ONE : Effacer la ressource pages identifiée                         |
| **films/{id}** | PUT          | JWT    | UPDATE ONE : Modifie la ressource pages identifiée                         |
