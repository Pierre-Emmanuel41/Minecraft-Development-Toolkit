cd minecraft-dictionary/dictionary/persistence
call mvn clean package install

cd ..
call mvn clean package install

cd ../minecraft-managers
call mvn clean package install

cd ..
call mvn clean package install

cd ../minecraft-scoreboards
call mvn clean package install

cd ..
mvn clean package install
