# sportradar

Sportradar demo app

Author: Felipe Monteiro de Carvalho

### Building and running test in the command line

```
./gradlew build
```

### Assumptions

1. Implemented the simplest solution which is that the ScoreBoard constains a list of games. 
The most common operation probably will be reading, if we want the list to be pre-sorted so 
that reading it is O(1) we could use SortedSet/TreeSet, but this will only work if we support 
only 1 kind of sorting. Java apps are typically servers, so optimizing here might be pointless,
since caching the final response would anyway be much better and would include the transformation
to JSON.

2. Didn't use spring since this is a "library", to prevent locking the user into it

3. Used Domain Driven Development instead of classic OO design (like Qt, Cocoa, etc)

4. There was no requirement to support writing and reading concurrently,
but it could be implemented by using a "Flip/Flop" mechanism, in which during 
writes you populate the entire data in the unused version, and then simply switches 
which version is used by using an AtomicInteger increase. getGames() would select 
to return Flip or Flop based on this counter.

5. Assumed users of the library won't pass null anywhere

### Time complexities

1. startGame - List.add is O(1), worse case O(n) due to array enlargement

2. finishGame - O(n) for remove() in a List

3. updateScore - O(n) due to remove+add

4. getGamesSummary - O(n * log(n)) for sorting

For reference, see: https://www.baeldung.com/java-collections-complexity
