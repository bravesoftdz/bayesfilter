#!/bin/bash

download_story.sh 'http://www.dailymail.co.uk/sport/football/article-4408142/What-Messi-Ronaldo-doing-Mbappe-s-age.html' | grep -v '\[' > sport_Ronaldo-doing-Mbappe-s-age.txt
download_story.sh 'http://www.dailymail.co.uk/sport/football/article-4408978/Milan-sold-Chinese-group-end-months-uncertainty.html' | grep -v '\[' > sport_Chinese-group-end-months-uncertainty.txt
download_story.sh 'http://www.dailymail.co.uk/sport/article-4407894/Slow-starts-costing-Tottenham-title-race.html' | grep -v '\[' > sport_starts-costing-Tottenham-title-race.txt
download_story.sh 'http://www.dailymail.co.uk/sport/football/article-4408826/Liverpool-FC-Jurgen-Klopp-visibly-upset-Dortmund-bomb.html' | grep -v '\[' > sport_Klopp-visibly-upset-Dortmund-bomb.txt
download_story.sh 'http://www.dailymail.co.uk/sport/golf/article-4408474/Sergio-Garcia-sore-loser-claims-Padraig-Harrington.html' | grep -v '\[' > sport_sore-loser-claims-Padraig-Harrington.txt
download_story.sh 'http://www.dailymail.co.uk/sport/football/article-4408958/Dele-Alli-PFA-shortlist-says-Pochettino.html' | grep -v '\[' > sport_Alli-PFA-shortlist-says-Pochettino.txt
download_story.sh 'http://www.dailymail.co.uk/sport/sportsnews/article-4408938/UKAD-look-claim-Team-Sky-breached-no-needles-policy.html' | grep -v '\[' > sport_Sky-breached-no-needles-policy.txt
download_story.sh 'http://www.dailymail.co.uk/sport/football/article-4408572/How-Manchester-United-beat-Chelsea-TACKLE-KEOWN.html' | grep -v '\[' > sport_United-beat-Chelsea-TACKLE-KEOWN.txt
download_story.sh 'http://www.dailymail.co.uk/sport/football/article-4408488/Manchester-United-news-Wayne-Rooney.html' | grep -v '\[' > sport_Manchester-United-news-Wayne-Rooney.txt
