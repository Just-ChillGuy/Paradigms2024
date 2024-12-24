prime(N) :-
    N > 1,
    \+ has_factor(N, 2).

has_factor(N, L) :-
    L * L =< N,
    (N mod L =:= 0 ; NextL is L + 1, has_factor(N, NextL)).

composite(N) :-
    N > 1,
    \+ prime(N).

prime_divisors(1, []) :- !.
prime_divisors(N, Divisors) :-
    N > 1,
    prime_divisors(N, 2, [], Divisors).

prime_divisors(1, _, Divisors, Divisors) :- !.
prime_divisors(N, D, Acc, Divisors) :-
    N mod D =:= 0, !,
    NewN is N // D,
    append(Acc, [D], NewAcc),
    prime_divisors(NewN, D, NewAcc, Divisors).
prime_divisors(N, D, Acc, Divisors) :-
    next_divisor(D, NextD),
    prime_divisors(N, NextD, Acc, Divisors).

next_divisor(2, 3) :- !.
next_divisor(D, NextD) :-
    D >= 3,
    NextD is D + 2.

nth_prime(N, P) :-
    nth_prime(N, 1, 2, P).

nth_prime(N, Count, Current, P) :-
    prime(Current),
    Count =:= N, !,
    P = Current.

nth_prime(N, Count, Current, P) :-
    prime(Current), !,
    NextCount is Count + 1,
    NextCurrent is Current + 1,
    nth_prime(N, NextCount, NextCurrent, P).

nth_prime(N, Count, Current, P) :-
    NextCurrent is Current + 1,
    nth_prime(N, Count, NextCurrent, P).
