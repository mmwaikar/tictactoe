# TicTacToe

A sample implementation of Tic-Tac-Toe game in Scala. There is no UI implemented for now, and only a REST API is 
exposed.

## Technologies used

This application has been written using:

* [Scala 2.13.10](https://www.scala-lang.org/download/2.13.10.html)
* [Play framework 2.8.18](https://www.playframework.com/)

### Constants (or enums) used

A few constants (enums) have been used in the application -

* For **horizontal** positions - `Left, HCenter & Right`
* For **vertical** positions - `Top, VCenter & Bottom`

## Endpoints

Three endpoints are exposed which let us play this game -

* `GET /game/start` -> which starts the game and returns a JSON representation of `com.codionics.tictactoe.models.Game` 
case class. It represents the current state of the game in the `state` field, which contains `cells`, that represent the
`position` of a cell on the board and the `state` - whether it is occupied by **X**, or **O** or is an **Empty** cell. The
`status` and `remainingMoves` fields are calculated automatically.

* `PUT /game/move/x` -> for player X to move. We need to supply a JSON input to this endpoint which consists of `state` &
`position` fields. The value of the former field can be copied from the output of the `/game/start` endpoint, whereas,
for the latter, the constants mentioned above should be used (for horizontal & vertical positions). The `state` of the cell
is assumed to be `X` here.

* `PUT /game/move/o` -> for player O to move. We need to supply a JSON input to this endpoint which consists of `state` &
`position` fields. The value of the former field can be copied from the output of the previous `/game/move/x` endpoint,
whereas, for the latter, the constants mentioned above should be used (for horizontal & vertical positions). The `state` 
of the cell is assumed to be `O` here.

**NOTE:** The idea of copying the previous state and then specifying the next move is used to keep the endpoints 
**stateless**, i.e. the server does not remember the state of the game at any point in time. It just calculates the next 
state, based on the previous state and the new position (for `X` or `O`) provided.

### Validations

1. If one tries to play at the same position twice, an error is returned.
2. If the same player tries to play twice, an error is returned.

## Deliverable

A `tictactoe-1.0-SNAPSHOT.zip` is provided. Let's say you unzip it to a `tictactoe-1.0-SNAPSHOT` folder. You'll notice a 
folder structure such as -

```
tictactoe-1.0-SNAPSHOT
|__bin
|    tictactoe
|    tictactoe.bat
|__conf
|__docs
|____illegal-moves
|      o-2-left-top.json
|      x-1-left-top.json
|____x-wins
|      o-1-hcenter-top.json
|      o-2-hcenter-vcenter.json
|      x-1-left-top.json
|      x-2-left-vcenter.json
|      x-3-left-bottom.json
|__lib
|__logs
```

You can run the `tictactoe` bash script (on *Nix systems). It will run a server on your `localhost` on port `9000`. Then
you can test the application using the `GET` and the two `PUT`(s) for `X` & `O` in succession.

### Sample JSON files

A few sample JSON files are provided in the `docs` folder. The `illegal-moves` folder has JSONs for two moves - 
`x-1 & o-2`. The `x-wins` folder has 5 JSONs for moves in the order - `x-1, o-1, x-2, o-2 & x-3`.

### Tests

This is a typical Scala application (that follows Java conventions) and the tests are in the 
`test\com\codionics\tictactoe` folder. Majority of the tests are in the `GameStateSpec` class. The tests could be run
through `sbt` using the `test` command.

### Features that can be added

Currently, the endpoints return an error (if any) as a string. Some proper JSON could be returned in the erroneous cases.

## Future Improvements

* [Scala 3.2.1](https://www.scala-lang.org/download/3.2.1.html) should be used, which introduces a lot of new features,
including **Discriminated Unions** which help immensely in modeling the domain nicely (in a more type safe fashion).

* Typed functional (effect systems) libraries like [Cats Effect](https://typelevel.org/cats-effect/) or 
[ZIO](https://zio.dev/) should be used for even better / pure FP code.
