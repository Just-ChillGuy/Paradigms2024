map_build(ListMap, TreeMap) :-
    length(ListMap, Length),
    map_build(ListMap, Length, TreeMap, _).

map_build(List, 0, nil, List).
map_build(List, Length, tree(Key, Value, LeftTree, RightTree), Rest) :-
    Length > 0,
    Middle is Length // 2,
    map_build(List, Middle, LeftTree, [(Key, Value) | RestLeft]),
    RestLength is Length - Middle - 1,
    map_build(RestLeft, RestLength, RightTree, Rest).

map_get(tree(Key, Value, Left, Right), KeySearch, Result) :-
    (   KeySearch = Key ->
        Result = Value
    ;   KeySearch @< Key ->
        map_get(Left, KeySearch, Result)
    ;   map_get(Right, KeySearch, Result)
    ).

map_lastKey(tree(Key, _, _, nil), Key).
map_lastKey(tree(_, _, _, Right), Key) :-
    map_lastKey(Right, Key).

map_lastValue(tree(_, Value, _, nil), Value).
map_lastValue(tree(_, _, _, Right), Value) :-
    map_lastValue(Right, Value).

map_lastEntry(tree(Key, Value, _, nil), (Key, Value)).
map_lastEntry(tree(_, _, _, Right), Entry) :-
    map_lastEntry(Right, Entry).
