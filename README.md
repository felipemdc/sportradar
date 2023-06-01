# sportradar

Sportradar demo app

Author: Felipe Monteiro de Carvalho

### Assumptions

1> Assumed a score board won't contain too many games (< 10ˆ4 ?), but
it could be accessed by many users, so attempted to optimize for reading
while sacrificing write speed if necessary. The simplest way to achieve this
is to keep the in-memory data as a pre-sorted list. This strategy won't work
if in the future we decide to add more sorting options.

2> Didn't use spring since it is a "library", to prevent locking the user into it

3> Used Domain Driven Development instead of classic OO design (like Qt)

4> There was no requirement to support writing and reading concurrently,
but it could be implemented by using a "Flip/Flop" mechanism, in which during 
writes you populate a new list in the unused version, and then simply switches 
which version is used by using an AtomicInteger increase. getGames() would select 
to return Flip or Flop based on this counter.

### Building and running test in the command line

./gradlew build

