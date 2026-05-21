# Refactoring Opportunities

| Local                    | Code Smell (Fowler)                     | Refactoring                             | Nº Aluno |
|--------------------------|-----------------------------------------|-----------------------------------------|--|
| Game::fireSingleShot     | Long Method                             | Extract Method                          | 123011 |
| Game::readEnemyFire      | Long Method                             | Extract Method                          | 123025 |
| Game::randomEnemyFire    | Long Method                             | Extract Method                          | 123025 |
| Game                     | Redundant Initialization                | Replace with Constant                   | 123011 |
| Ship                     | Data Class                              | Encapsulate Field                       | 110764 |
| Ship                     | Long Method                             | Extract Method                          | 110764 |
| Ship                     | Speculative Generality                  | Remove Dead Code                        | 110764 |
| Jogada                   | Data Class                              | Encapsulate Field                       | 123011 |
| Jogada                   | Long Parameter List                     | Introduce Parameter Object              | 110764 |
| Tasks                    | Long Method                             | Extract Method                          |  |
| Tasks                    | Switch Statements                       | Decompose Conditional                   |  |
| PdfExport                | Speculative Generality                  | Remove Method / Replace with Logging    | 123011 |
| Carrack                  | Speculative Generality                  | Safe Delete                             |  |
| pom.xml                  | Inappropriate Intimacy                  | Replace Dependency / Upgrade Dependency |  |
| ---------------------    | ------------------------                | ------------------------------------    | -------- |
| Game::randomEnemyFire    | Long Method                             | Extract Method                          | 123025 |
| Game::printBoard         | Long Method                             |                                         |  |
| Ship::Ship               | Redundant Assignment / Variable Reuse   | Remove Redundant Assignment             | 123025 |
| Tasks::menu              | Long Method                             |                                         |  |
| Move::processEnemyFire   | Long Method                             |                                         |  |
| Messages::goodbyeMessage | Duplicate Code                          |                                         |  |
| Messages::invalidCommand | Duplicate Code                          |                                         |  |
| Messages::boardCaption   | Duplicate Code                          |                                         |  |
| Messages::status         | Duplicate Code                          |                                         |  |
| Carrack::Carrack         | Duplicate Code                          | Merge switch cases                      | 123025 |
| Frigate::Frigate         | Duplicate Code                          | Merge switch cases                      | 123025 | 
| Caravel::Caravel         | Duplicate Code                          | Merge switch cases                      | 123025 |