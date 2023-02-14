# Video-Entertainment-Platform-Database
Video database containing detailed entries for movies, shows &amp; actors and also able to provide personalized recommendations to users - Implemented in Java

Calugaritoiu Ion-Victor 

OOP - VideosDB

In cadrul temei am implementat o platforma care furnizeaza diverse informatii
despre utilizatorii, filmele, serialele si actorii din database-ul sau.

Entry point-ul programului se afla in clasa Main, care apeleaza metoda
doCommands (UserCommands), unde  sunt apelate la randul lor metodele
corespunzatoare comenzilor (ActionInputData) din input (Input).

Dupa ce este stabilit tipul unei comenzi (command/query/recommendation), sunt
apelate urmatoarele metode care returneaza un StringBuilder output:
    - Command.doCommand: realizeaza una dintre cele 3 actiuni directe pe care
    le poate realiza un utilizator: view, rate, favorite(pentru care sunt
    apelate alte metode la randul lor);

    - Query.doQuery: reprezinta cautari globale in baza de date realizate de
    catre utilizatori; returneaza liste de actori/video-uri/utilizatori pe baza
    unor proprietati/filtre din input;

    - Recommendation.doRecommendation: reprezinta cautari in baza de date de
    video-uri realizate de catre utilizatori; returneza un video/o lista de
    video-uri in functie de tipul de recomandare(bazata pe numarul de vizuali-
    zari, cati utilizatori au marcat video-ul ca favorit, etc.) si pe tipul de
    cont al utilizatorului care solicita recomandarea(BASIC/PREMIUM);

Toate cele 3 tipuri de comenzi pe care le poate trimite un utilizator, dupa
ce sunt facute cautarile/actiunile dorite, returneaza un mesaj de tip
StringBuilder, care este scris in fisierul JSON de output (writeFile - Writer).

Pentru a indeplini sarcinile dorite de catre utilizatori in comenzi, query-uri
si recomandari, am adaugat diverse campuri si metode in clasele deja existente
ale proiectului. Spre exemplu:
    - in clasa ShowInput am declarat metodele abstracte de getter si setter
    pentru campul average prezent in MovieInputData si SerialInputData, valori
    care se calculeaza diferit.
    - in clasa MovieInputData am adaugat campul ratings, care memoreaza notele
    oferite de catre utilizatori;
    - in clasa SeriesInputData am adaugat campul totalDuration si metodele de
    getter si setter pentru acesta(durata totala = suma duratelor sezoanelor);
    - in clasa MultiComparator am implementat metode care sorteaza liste si
    map-uri in functie de criteriile specificate in input + tipul de query/
    recomandare;
    - in clasa Utils am implementat diverse metode care au scopul de a returna
    utilizatori/video-uri dupa nume/comanda din input;