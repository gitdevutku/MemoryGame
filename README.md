# Memory Game

Memory Game is a popular game where players need to find pairs of cards with the same image. This Java Swing-based game generates random positions for each card on the board and ensures that each card is placed exactly twice.

## How to Play

1. Run the game.
2. Flip over cards by clicking on them.
3. Try to find all pairs of cards before running out of attempts.

## Scoring

The player's score increases each time they successfully find a pair of cards.

## Winning the Game

The player wins the game by finding all pairs of cards before running out of attempts.

## Resetting the Game

The game can be reset by clicking the "Reset" button.

## Updating Labels

The labels at the top of the screen show the number of attempts made so far and the player's current score.

## Generating Markdown

The Markdown for this document was generated using a Python script and the `markdownify` library. The script takes the game's code and docstrings as input and converts them into a Markdown-formatted document.

Here is an example of how to generate the Markdown:

```python
import markdownify
from memory_game import MemoryGame

code = markdownify.markdownify(inspect.getsource(MemoryGame))
docstrings = markdownify.markdownify(inspect.getdoc(MemoryGame))

with open("MemoryGame.md", "w") as f:
    f.write("# Memory Game\n\n")
    f.write(docstrings)
    f.write("\n## Code\n\n")
    f.write(code)
