insert into artikels(naam,aankoopprijs,verkoopprijs,houdbaarheid,garantie,soort, artikelgroepId)
values
    ('testfood', 90, 105, 6, null, 'F', 1),
    ('testnonfood', 90, 110, null, 28, 'NF', 2);
insert into kortingen(artikelid, vanafAantal, percentage) values
    ((select id from artikels where naam = 'testfood'), 1, 10);