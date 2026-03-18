package battleship.ai.utils;

import battleship.Game;

public class PromptBuilder {

    public static String build(Game game, String logs) {
        return """
                Considere que é um perito no famoso jogo da Batalha Naval, aqui numa versão do tempo
                dos Descobrimentos Portugueses, jogado num tabuleiro com linhas identificadas de A a
                J e colunas de 1 a 10. Deve começar por criar secretamente a sua frota de 11 navios:
                • 4 Barcas (1 posição na quadrícula)
                • 3 Caravelas (2 posições na quadrícula)
                • 2 Naus (3 posições na quadrícula)
                • 1 Fragata (4 posições na quadrícula)
                • 1 Galeão (5 posições na quadrícula, em forma de T, com um corpo de 3 posições
                e, numa das suas extremidades, uma posição adicional para cada lado, correspondentes às chamadas 'asas da ponte')
                Os navios podem ser gerados com qualquer orientação no tabuleiro, desde que não
                toquem uns nos outros, nem mesmo por um canto (i.e. na diagonal), embora possam
                estar encostados às margens do tabuleiro. Repare que enquanto uma caravela, uma nau
                ou uma fragata pode ter duas orientações possíveis (norte-sul ou este-oeste), um galeão
                pode ter quatro orientações diferentes, consoante o braço do T esteja virado para norte,
                sul, este ou oeste. Já a questão da orientação não se coloca para uma barca, porque
                ocupa apenas uma posição no tabuleiro.
                A nossa interação será através de objetos JSON, quer para a rajada de 3 tiros, quer para
                a correspondente resposta. Uma rajada tem o seguinte formato:
                {
                [ {'row': 'A', 'column': 5},
                {'row': 'C', 'column': 10},
                {'row': 'F', 'column' : 5} ]
                }
                A resposta a uma rajada é feita em conjunto e não tiro a tiro, como nos seguintes 3
                exemplos:
                • Uma barca ao fundo, um tiro numa nau e um tiro na água
                {
                'validShots': 3,
                'sunkBoats': [ {'count': 1, 'type': 'Barca'} ],
                'repeatedShots': 0,
                'outsideShots': 0,
                'hitsOnBoats': [ {'hits': 1, 'type': 'Nau'} ],
                'missedShots': 1
                }
                • Um tiro fora do tabuleiro, um tiro repetido e um tiro na água (o único válido)
                {
                'validShots': 1,
                'sunkBoats': [ ],
                'repeatedShots': 1,
                'outsideShots': 1,
                'hitsOnBoats': [ ],
                'missedShots': 1
                }
                • Um tiro numa nau, um tiro no galeão e uma caravela ao fundo
                {
                'validShots': 3,
                'sunkBoats': [{'count': 1, 'type': 'Caravela'}],
                'repeatedShots': 0,
                'outsideShots': 0,
                'hitsOnBoats': [{'hits': 1, 'type': 'Nau'}, {'hits': 1, 'type': 'Galeao'}],
                'missedShots': 0
                }
                O campo “repeatedShots” indica o número de tiros em posições já atingidas em jogadas
                anteriores, ou na própria jogada (em ambos os casos, uma péssima estratégia porque
                desperdiça tiros, só admissível por distração do jogador). O campo “outsideShots” indica
                o número de tiros em posições exteriores ao tabuleiro (ex.: B12, K4 ou L14), um erro
                também só admissível por distração do jogador. O campo “missedShots” corresponde ao
                número de tiros na água.
                Considere agora a seguinte tática de geração de rajadas de tiros.
                • Crie um Diário de Bordo com o registo de cada rajada disparada, numerando-as sequencialmente (Rajada 1, 2, 3...). Guarde as coordenadas exatas de cada tiro e o
                respetivo resultado (Água, Nau atingida, Barca afundada, etc.). A memória é a principal arma de um bom estratega.
                • Não dispare fora dos limites do mapa (ex: Z99) nem repita tiros em coordenadas
                já testadas. A única exceção para este desperdício de pólvora é a última rajada do
                jogo, apenas para perfazer os 3 tiros obrigatórios quando a frota inimiga já estiver
                irremediavelmente no fundo do mar.
                • Se atingir um navio numa rajada, dispare nas posições contíguas (Norte, Sul, Este,
                Oeste) na jogada seguinte para descobrir a orientação da embarcação e acabar de a
                afundar. No entanto, se a rajada anterior confirmar que o navio já foi afundado, não
                dispare para as posições contíguas, pois os navios nunca estão encostados.
                • Como as Caravelas, Naus e Fragatas são linhas retas, um tiro certeiro significa que o
                resto do navio está na horizontal ou na vertical. Como os navios não se podem tocar
                (nem sequer nos cantos), as posições diagonais a um tiro certeiro são garantidamente
                água (a única exceção é o corpo do Galeão, devido à sua forma em T). Evitar estas
                diagonais poupa imensos tiros.
                • Quando o relatório de uma rajada confirmar que um navio foi afundado (ex: Fragata de
                4 posições), analise os dados do seu Diário de Bordo para identificar exatamente onde
                caíram esses 4 tiros. Confirmada a posição exata da carcaça, marque todas as quadrículas adjacentes (o halo de 1 posição em redor do navio) como água intransitável.
                É impossível haver outra embarcação nesse perímetro.
                • Se a sua frota for toda afundada, declare a derrota com honra. Em contrapartida, seja
                um vencedor magnânimo se for o inimigo a render-se com os navios todos no fundo
                do oceano!
                Responde APENAS com uma única linha no formato:
                                rajada A1 A2 A3
                
                                Regras de resposta:
                                • Exatamente 3 coordenadas
                                • Sem texto adicional
                                • Sem explicações
                                • Sem JSON
                
                                Exemplo de resposta válida:
                                rajada B2 C3 D4
                """.formatted(logs);
    }
}
