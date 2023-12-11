package hu.elte.inf.szofttech2023.team3.spacewar.gameplay;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.Color;
import java.util.List;

import org.junit.jupiter.api.Test;

import hu.elte.inf.szofttech2023.team3.spacewar.controller.GameController;
import hu.elte.inf.szofttech2023.team3.spacewar.controller.generator.SpaceGenerator;
import hu.elte.inf.szofttech2023.team3.spacewar.controller.generator.cases.MinimalCaseSpaceGenerator;
import hu.elte.inf.szofttech2023.team3.spacewar.model.GameState;
import hu.elte.inf.szofttech2023.team3.spacewar.model.game.Player;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.Planet;
import hu.elte.inf.szofttech2023.team3.spacewar.view.FieldPosition;

class GamePlayIntegationTest {

    @Test
    void whenMinimal_ifConquerPlanet_thenOwnerIsSet() {
        var player1 = new Player(1, "Blue", Color.BLUE);
        var player2 = new Player(2, "Red", Color.RED);
        var players = List.of(player1, player2);
        SpaceGenerator spaceGenerator = new MinimalCaseSpaceGenerator(players);
        GameController controller = new GameController(new MockDisplayEngine(5, 5), spaceGenerator, players);
        GameState gameState = controller.getGameState();
        
        assertThat(((Planet) gameState.getSpace().getObjectAt(FieldPosition.of(1, 2))).getOwner()).isSameAs(player2);
    }
    
}
