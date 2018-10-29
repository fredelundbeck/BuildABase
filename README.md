# BuildABase

School project

It should be able to do CRUD operations on all tables, and maintain constraints fx. for ID.  
Only IDs of other tables should be represented in other tables, if at all.  
We may alter the database structure.  
We could programatically create indexes for all tables, but it may not make sense for all of them, it depends on how often we expect to read versus write to it, as writing means recreating the index.

## Project structure

Suggested structure for the database files, so we don't have to have them on GitHub and so our path and name of them is consistent for programming purposes?  
They're all called data.tsv by default so they need to be renamed.

/database - 7 files:

1. database/name_basics.tsv
2. database/title_akas.tsv
3. database/title_basics.tsv
4. database/title_crew.tsv
5. database/title_episode.tsv
6. database/title_principals.tsv
7. database/title_ratings.tsv
