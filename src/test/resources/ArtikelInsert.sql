insert into artikels(naam,aankoopprijs,verkoopprijs,houdbaarheid,garantie,soort)
values
    ('testfood', 90, 105, 6, null, 'F'),
    ('testnonfood', 90, 110, null, 28, 'NF');
insert into kortingen(artikelid, vanafAantal, percentage) values
    ((select id from artikels where naam = 'testfood'), 1, 10);