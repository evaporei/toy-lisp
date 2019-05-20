# clisp

A PoC of a lisp REPL interpreter in Clojure

I made this project because I wanted to see how it would look like to make the same lisp REPL interpreter, but instead of [Haskell]([https://github.com/otaviopace/hisp](https://github.com/otaviopace/hisp)) (yeah, I've made this in `Haskell` too), in `Clojure`.

Also, because it is almost the same program in two different languages, I do have a [commit](https://github.com/otaviopace/hisp/tree/1f9c6ec017ad9c2a96f8aca7e5ae118be4bda53c) in the `Haskell` project which corresponds almost to the same code in here.

Interestingly enough, the code is almost the same size (`Haskell`: ~111 SLOC vs `Clojure`: ~130 SLOC), even though `Haskell` is a strongly typed language. Not that lines of code are a good metric anyway haha.

## Usage

You will need to have `leiningen` installed on your computer.

    $ lein run

<p align="center">
  <img width="460" alt="terminal-working-version" src="https://user-images.githubusercontent.com/15306309/57993564-eefd4b00-7a8f-11e9-9be9-e3199310de2f.png">
</p>

## Language Features

- Numbers (`Float`)
- Addition (`+`)

That's all, it has few features because it is a proof of concept.


## License

Copyright ¬© 2019 FIXME

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
