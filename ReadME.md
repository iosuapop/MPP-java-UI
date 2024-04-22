Problema 7.
Organizatorii unui teledon în scopuri caritabile folosesc un sistem soft pentru gestiunea donațiilor făcute în
timpul emisiunii TV (teledonul) pentru diferite cazuri caritabile. În cadrul emisiunii, mai mulți voluntari
răspund la telefon și înregistrează donațiile. Fiecare voluntar folosește o aplicație desktop cu următoarele
funcționalități:
1. Login. După autentificarea cu succes, o nouă fereastră se deschide în care sunt afișate toate cazurile
   caritabile și suma totală strânsă până acum pentru fiecare caz.
2. Donație. Când se face o noua donație prin telefon, voluntarul alege cazul caritabil și introduce numele
   donatorului, adresa acestuia, numărul de telefon și suma donată. Dupa salvarea donației, lista de cazuri
   caritabile și suma totală donata pentru fiecare caz este actualizată pentru toți voluntarii.
3. Căutare. O persona poate dona sume de bani pentru mai multe cazuri. La înregistrarea unei donații
   voluntarul poate căuta un donator introducând o parte din numele acestuia. Rezultatele sunt afișate în alta
   lista/alt tabel de unde voluntarul poate alege/selecta. Când un donator este selectat, câmpurile
   corespunzătoare numelui, adresei și telefonului se completează automat.
4. Logout.

Tabele baza de date:

User{
int id (pk auto increment),
varchar(50) username (not null),
varchar(50) password (not null);
}

Donation{
int id (pk auto increment),
int id_charity (fk -> charity),
int id_donor (fk -> charity),
float amount;
}

Donor{
int id (pk aauto increment),
varchar(50) name (not null),
varchar(15) telephone (not null),
varchar(100) adress (not null);
}

Charity{
int id (pk auto increment),
vaarchar(50) nume (not null);
}

Functionalitati:
- login (username, password[criptata]) -> cautam sa vedem daca exista un match : bool
- afisare lista cazuri caritabile
- afisare suma totala stransa pentru fiecare caz

-creeare donatie Donation(charity,name,adress,telephone)
-update suma totala stransa pentru fiecare caz

-cautare donator(dupa o parte din nume) -> lista/tabel [view]
-select a donor (dintr-o lista)

-logout

Repository:

User :
+ login "criptat"
+ add user
+ logout

Charity :
+ add charity
+ delete charity
+ show_all charity (cases)
+ get_charity_by_id

Donations :
+ add donations
+ sum_charity (calcul/update suma pentru fiecare caz)

Donor :
+ add donor
+ get_donor_by_id
+ get_all_donors

Utils :
+ [view] get_all_donors_name (o parte din nume)



