# Refactoring Opportunities

| Local | Code Smell (Fowler) | Refactoring | Nº Aluno |
|-------|---------------------|-------------|----------|
| Game::fireSingleShot | Long Method | Extract Method | 123011 |
| Game::readEnemyFire | Long Method | Extract Method |  |
| Game::randomEnemyFire | Long Method | Extract Method |  |
| Game | Redundant Initialization | Replace with Constant | 123011|
| Ship | Data Class | Encapsulate Field | |
| Ship | Long Method | Extract Method |  |
| Ship | Speculative Generality | Remove Dead Code |  |
| Jogada | Data Class | Encapsulate Field | 123011 |
| Jogada | Long Parameter List | Introduce Parameter Object |  |
| Tasks | Long Method | Extract Method | |
| Tasks | Switch Statements | Decompose Conditional |  |
| PdfExport | Speculative Generality | Remove Method / Replace with Logging | 123011 |
| Carrack | Speculative Generality | Safe Delete | |
| pom.xml | Inappropriate Intimacy | Replace Dependency / Upgrade Dependency |  |
