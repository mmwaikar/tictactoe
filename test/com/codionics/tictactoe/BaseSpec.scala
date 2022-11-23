package com.codionics.tictactoe

import org.scalatest.{AppendedClues, BeforeAndAfterAll, EitherValues, OptionValues}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers._

abstract class BaseSpec
    extends AnyWordSpec
    with should.Matchers
    with OptionValues
    with EitherValues
    with AppendedClues
    with BeforeAndAfterAll
