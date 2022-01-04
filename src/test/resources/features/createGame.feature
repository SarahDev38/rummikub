Feature: Débuter un nouveau jeu
  En tant qu utilisateur, je veux démarrer un nouveau jeu

  Scenario: Enregistrer son nom
    Given un utilisateur veut jouer
    When il saisit son nom "Sarah"
    Then un joueur porte le nom "Sarah" et détient 14 cartes
