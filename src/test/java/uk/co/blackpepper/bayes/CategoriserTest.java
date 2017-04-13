package uk.co.blackpepper.bayes;

import org.junit.Ignore;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by davidg on 13/04/2017.
 */
public class CategoriserTest {

    @Test
    public void canFindJohnGrishamTitles() {
        //<editor-fold desc="Given">
        Categoriser categoriser = new Categoriser()
                .category("grisham", new StringSampleSource(
                        "A Time to Kill",
                        "The Firm ",
                        "The Pelican Brief",
                        "The Client",
                        "The Chamber",
                        "The Rainmaker",
                        "The Runaway Jury",
                        "The Partner",
                        "The Street Lawyer",
                        "The Testament",
                        "The Brethren",
                        "A Painted House",
                        "Skipping Christmas",
                        "The Summons",
                        "The King of Torts",
                        "Bleachers",
                        "The Last Juror",
                        "The Broker",
                        "The Innocent Man",
                        "Playing for Pizza",
                        "The Appeal",
                        "The Associate",
                        "Ford County",
                        "Theodore Boone: Kid Lawyer",
                        "The Confession",
                        "Theodore Boone: The Abduction",
                        "The Litigators ",
                        "Theodore Boone: The Accused",
                        "Calico Joe",
                        "The Racketeer",
                        "Theodore Boone: The Activist",
                        "Sycamore Row ",
                        "Gray Mountain",
                        "Theodore Boone: The Fugitive",
                        "Rogue Lawyer",
                        "Partners (a \"Rogue Lawyer\" short story)",
                        "Theodore Boone: The Scandal",
                        "Witness to a Trial (a digital short story)"
                ))
                .category("dickens", new StringSampleSource(
                        "Great Expectations",
                        "Our Mutual Friend",
                        "David Copperfield",
                        "Bleak House",
                        "Little Dorrit",
                        "Oliver Twist",
                        "Nicholas Nickleby",
                        "Dombey and Son",
                        "The Pickwick Papers",
                        "The Selected Letters of Charles Dickens"
                ));
        //</editor-fold>

        assertEquals("grisham", categoriser.getProbableCategoryFor("The Whistler"));
        Map<String, Double> interestingWords = categoriser.interestingWords("The Whistler", "grisham");
        double probability = interestingWords.get("The");
        assertTrue(probability > 0.5);
    }

    @Test
    public void aNonSportStoryDoesNotLookLikeSport() throws IOException {
        //<editor-fold desc="Given">
        Categoriser categoriser = new Categoriser()
                .category("sport", new DirectorySampleSource("./samples/sport"))
                .category("nonsport", new DirectorySampleSource("./samples/nonsport"));
        //</editor-fold>

        //<editor-fold desc="When">
        String text = "LUCCA, Italy — Secretary of State Rex W. Tillerson said on Tuesday that the reign of " +
                "President Bashar al-Assad of Syria was “coming to an end” and warned that Russia was at risk " +
                "of becoming irrelevant in the Middle East by continuing to support him.\n" +
                "\n" +
                "Mr. Tillerson, in comments made just before he traveled to Moscow for a high-stakes summit " +
                "meeting, sought to clear up the United States’ position on Syria while also declaring that President Vladimir V. Putin of Russia needed to choose whether to side with Mr. Assad or the West.\n" +
                "\n" +
                "Russia can be a part of the discussions “and play an important role,” Mr. Tillerson said at " +
                "a Group of 7 meeting in Lucca, Italy, or it “can maintain its alliance with this group, " +
                "which we believe is not going to serve Russia’s interests longer term.”\n" +
                "\n" +
                "In a preview of his coming meetings in Moscow, he then added, “Only Russia can answer that " +
                "question.”\n" +
                "\n" +
                "Continue reading the main story\n" +
                "RELATED COVERAGE\n" +
                "\n" +
                "\n" +
                "On Trump’s Syria Strategy, One Voice Is Missing: Trump’s APRIL 10, 2017\n" +
                "Feud Over Syria Missile Strike May Have an Upside for U.S. and Russia APRIL 11, 2017\n" +
                "\n" +
                "Tillerson, on Eve of Russia Trip, Takes Hard Line on Syria APRIL 9, 2017\n" +
                "\n" +
                "Worst Chemical Attack in Years in Syria; U.S. Blames Assad APRIL 4, 2017\n" +
                "\n" +
                "Dozens of U.S. Missiles Hit Air Base in Syria APRIL 6, 2017\n" +
                "The United States carried out cruise missile strikes last week after a chemical weapons " +
                "attack in Syria — where a civil war has raged for six years — that has been widely attributed " +
                "to Mr. Assad and his military forces.\n" +
                "\n" +
                "Russia’s increasingly close alliance with Mr. Assad has allowed it to expanded its military " +
                "presence in the Middle East and has contributed to what is widely viewed as a renewed relevance " +
                "in the region. Mr. Tillerson’s suggestion that Russia’s ties with Mr. Assad would diminish the " +
                "country’s standing contradicts Moscow’s recent experience.\n" +
                "\n" +
                "With his comments, Mr. Tillerson tried to untangle the confusing mix of signals from the Trump " +
                "administration over whether the United States conducted the missile strike for humanitarian or " +
                "national security reasons, and whether the Trump administration seeks an immediate change in " +
                "government in Syria.\n" +
                "\n" +
                "“We do not want the regime’s uncontrolled stockpile of chemical weapons to fall into the hands " +
                "of ISIS or other terrorist groups who could and want to attack the United States or our " +
                "allies,” he said at a brief news conference in Lucca, referring to the Islamic State. “Nor can " +
                "we accept the normalization of the use of chemical weapons by other actors or countries in " +
                "Syria or elsewhere.”\n" +
                "\n" +
                "Shortly after speaking, Mr. Tillerson got up from a round wooden table in the Palazzo Ducale, " +
                "where he was attending a summit meeting of foreign ministers from G-7 countries, and headed " +
                "to the airport.\n" +
                "\n" +
                "Mr. Tillerson said that the American priority in Syria and Iraq “remains the defeat of ISIS,” " +
                "and that Mr. Assad does not have a place in Syria’s future.\n" +
                "\n" +
                "“I think it is clear to all of us that the reign of the Assad family is coming to an end,” the " +
                "secretary of state said. “But the question of how that ends, and the transition itself could " +
                "be very important, in our view, to the durability, the stability inside of a unified Syria.”\n" +
                "\n" +
                "The Interpreter Newsletter\n" +
                "Understand the world with sharp insight and commentary on the major news stories of the week.\n" +
                "\n" +
                "\n" +
                "Enter your email address\n" +
                " Sign Up\n" +
                "\n" +
                "Receive occasional updates and special offers for The New York Times's products and services.\n" +
                "\n" +
                "MANAGE EMAIL PREFERENCES PRIVACY POLICY\n" +
                "“We are not presupposing how that occurs,” he said, but added that Mr. Assad’s continued use " +
                "of chemical weapons had ended his legitimacy.\n" +
                "\n" +
                "Despite the blunt criticism from Mr. Tillerson, the Italian foreign minister, Angelino Alfano, " +
                "said there was “no consensus” on whether to toughen sanctions on Russia, as sought by his " +
                "British counterpart, Boris Johnson.\n" +
                "\n" +
                "Mr. Alfano said that any effort to isolate Russia “would be wrong,” adding that the dominant " +
                "position at the G-7 “was dialogue with Russia, not pushing Russia into a corner.”\n" +
                "\n" +
                "The Italian government has long been uneasy about punishing Russia, which faces sanctions " +
                "imposed for its actions in eastern Ukraine, because many Italian companies have significant " +
                "business interests there.\n" +
                "\n" +
                "“Russia has the power to put pressure on Assad,” Mr. Alfano said.\n" +
                "\n" +
                "Mr. Tillerson’s trip to Moscow is the first by a high-level Trump administration official, and " +
                "the State Department is not expecting any breakthroughs with the Russian government over " +
                "the myriad issues that increasingly divide the two governments, according to a senior " +
                "American official.\n" +
                "\n" +
                "Rather, the purpose of the trip is to make plain the areas of disagreement, said the official, " +
                "who spoke on the condition of anonymity in keeping with State Department practice.\n" +
                "\n" +
                "Even though Mr. Tillerson received a friendship award from Mr. Putin’s government when he " +
                "served as chief executive of Exxon Mobil, he has taken a tough line on Russia since joining " +
                "the administration. He repeated his view that Russia was either incompetent or inattentive in " +
                "its failure to secure and destroy Mr. Assad’s chemical weapons stockpiles.\n" +
                "\n" +
                "“But this distinction doesn’t much matter to the dead,” he said. “We can’t let this " +
                "happen again.”\n" +
                "\n" +
                "Russia has denied that Mr. Assad conducted a chemical attack, saying the poison gas was the " +
                "result of an assault by rebels. But Mr. Tillerson said he hoped to convince the Russians " +
                "that their continued support of Mr. Assad has become embarrassing for Russia.\n" +
                "\n" +
                "“And now Assad has made the Russians look not so good,” Mr. Tillerson said.";

        double probability = categoriser.getProbabilityInCategory(text, "sport");
        //</editor-fold>

        //<editor-fold desc="Then">
        assertTrue(probability < 0.1);
        //</editor-fold>
    }


    @Test
    public void aSportStoryLooksLikeSport() throws IOException {
        //<editor-fold desc="Given">
        Categoriser categoriser = new Categoriser()
                .category("sport", new DirectorySampleSource("./samples/sport"))
                .category("nonsport", new DirectorySampleSource("./samples/nonsport"));

        String text = "As Michael Pineda cut through the Tampa Bay Rays’ lineup Monday afternoon, stacking one " +
                "out on top of another on top of another, an uncommon energy percolated throughout Yankee Stadium.\n" +
                "\n" +
                "Outs began to be punctuated by increasingly enthusiastic cheers. Fans began to rise whenever " +
                "Pineda reached two strikes on a hitter. When Brett Gardner raced toward the left-field line " +
                "to snag Kevin Kiermaier’s seventh-inning liner, the sellout crowd for the Yankees’ home opener " +
                "let out a roar, acknowledging what was generally unspoken: Pineda was only seven outs from a " +
                "perfect game.\n" +
                "\n" +
                "“You’re thinking it’s going to be another special day here at the Stadium,” Manager Joe " +
                "Girardi said. “I thought he had a shot.”\n" +
                "\n" +
                "The dreams of a perfect game — or even a no-hitter — vanished on the next at-bat, however, " +
                "when Evan Longoria hooked a belt-high slider into the left-field corner for a double. And a " +
                "shutout disappeared on Logan Morrison’s solo homer in the eighth. But those did little to " +
                "dampen the enthusiasm surrounding the Yankees’ 8-1 victory over the Rays.\n" +
                "\n" +
                "Continue reading the main story\n" +
                "Photo\n" +
                "\n" +
                "Brett Gardner, who singled to lead off the game, raced to third on Matt Holliday’s single. " +
                "He scored the Yankees’ first run in the third inning, on a double by Jacoby Ellsbury. Credit " +
                "Ben Solomon for The New York Times\n" +
                "For a team that lost four of its first six games and that had already endured its share of " +
                "dispiriting injury news, a performance like Pineda’s had a buoyant effect.\n" +
                "\n" +
                "Continue reading the main story\n" +
                "ADVERTISEMENT\n" +
                "\n" +
                "Continue reading the main story\n" +
                "\n" +
                "If the Yankees, who found out Monday that they would be without catcher Gary Sanchez for " +
                "four weeks with a strained muscle in his arm — in addition to missing shortstop Didi " +
                "Gregorius (shoulder) until May — are to stay afloat, they will need to see this type of " +
                "Pineda more often.\n" +
                "\n" +
                "Pineda, a 6-foot-7 right-hander with loose limbs and an ample paunch, has been a " +
                "confounding presence in the Yankees’ rotation for the last three seasons. Blessed with " +
                "the arsenal of an ace, Pineda has been consistently undone by head-scratching meltdowns. " +
                "Two years ago, he struck out 16 Baltimore hitters in a game. But he entered Monday with " +
                "a 23-28 record and a 4.15 earned run average with the Yankees. Last season, opponents " +
                "batted .328 against him with two outs.\n" +
                "\n" +
                "Pineda pledged to improved his focus this season, but in his first start, at Tampa Bay " +
                "last Wednesday, his third pitch was hit for a home run, he allowed two run-scoring hits " +
                "with two outs in the second, and he was gone before the fourth inning was finished.\n" +
                "\n" +
                "He looked like a different pitcher Monday.\n" +
                "\n" +
                "On a 76-degree, sun-kissed afternoon — a far cry from last year’s shivering 36-degree " +
                "home opener — Pineda was constantly ahead of hitters. He walked none; his slider was " +
                "devastating, responsible for 10 of his 11 strikeouts; and he used his changeup more " +
                "frequently and with great effect.\n" +
                "\n" +
                "Continue reading the main story\n" +
                "Photo\n" +
                "\n" +
                "The Yankees’ Aaron Judge celebrated after hitting a home run in the fourth inning. " +
                "Credit Ben Solomon for The New York Times\n" +
                "“The last time we faced him, some of the fastballs were just sprayed — weren’t really " +
                "close to the zone — and a lot of the sliders that he threw weren’t really close, either,” " +
                "Longoria said. “Early on he established he was throwing strikes and really made us kind of " +
                "try to attack him. He was just throwing the ball where he wanted to.”\n" +
                "\n" +
                "The Yankees gave Pineda a 1-0 lead in the third when Gardner struck out but reached on Alex " +
                "Cobb’s wild pitch, then raced home on Jacoby Ellsbury’s double into the right-center gap. " +
                "Aaron Judge hit a solo homer in the fourth, and Chase Headley added another in the seventh " +
                "before the Yankees turned the game into a rout with five runs in the eighth — two on " +
                "Starlin Castro’s homer.\n" +
                "\n" +
                "But the offense served only as a complement to Pineda, who by the fourth inning had the crowd " +
                "anticipating the chase for what would have been just the fourth perfect game in Yankees history. " +
                "The last do it, David Cone, was in the broadcast booth Monday. Cone’s perfect game in 1999 " +
                "was also the last time a Yankee threw a no-hitter. His catcher that day was Girardi.\n" +
                "\n" +
                "“I’m thinking the fourth inning, I look at the scoreboard and say, ‘Oh, a zero,’” Pineda said. " +
                "“I’m not really thinking of a no-hitter or a perfect game, but I’m thinking about giving a " +
                "good outing and giving an opportunity to my team.”\n" +
                "\n" +
                "But the drama was punctured in the seventh. Longoria laced the first pitch he saw into the " +
                "left-field corner, and this time Gardner did not have a chance. With the Yankees clinging to " +
                "a 2-0 lead, the pitching coach Larry Rothschild, who has been befuddled by Pineda’s " +
                "inconsistencies, visited Pineda to make sure he stayed composed.\n" +
                "\n" +
                "Continue reading the main story\n" +
                "\n" +
                "Photo\n" +
                "\n" +
                "Fans awaited the start of the game during opening ceremonies. Credit Ben Solomon for The New " +
                "York Times\n" +
                "Pineda did so, striking out Brad Miller on four pitches to end the inning.\n" +
                "\n" +
                "When Pineda walked off the mound an inning later to a standing ovation, he stayed in character " +
                "for the day: He used his uniform to wipe sweat from his face but showed little emotion.\n" +
                "\n" +
                "“Very calm and reserved, and he showed that out there,” said catcher Austin Romine, who filled " +
                "in for Sanchez. “There was no moment where it got out of hand. There was no moment where he " +
                "missed a pitch and it was frustration. It was just: ‘Give me the ball. I’m going to make " +
                "another pitch.’”\n" +
                "\n" +
                "Sports\n" +
                "Get the big sports news, highlights and analysis from Times journalists, delivered to your " +
                "inbox every week.\n" +
                "\n" +
                "\n" +
                "Enter your email address\n" +
                " Sign Up\n" +
                "\n" +
                "Receive occasional updates and special offers for The New York Times's products and services.\n" +
                "\n" +
                "SEE SAMPLE MANAGE EMAIL PREFERENCES PRIVACY POLICY\n" +
                "Romine was an unlikely partner during Pineda’s gem. He had caught him only six times and not " +
                "since last July 25, when Pineda pitched one of his best games of the season: a 2-1 victory over " +
                "Houston’s Dallas Keuchel, a Yankees tormentor.\n" +
                "\n" +
                "The moment was also special for Romine, who was drafted by the Yankees 10 years ago but is " +
                "still seeking to establish himself in the major leagues. He was a capable backup last season " +
                "and figured to do the same until Sanchez was injured. But Monday, he was introduced with the " +
                "starting lineup, and afterward, as he answered questions at his locker, his son stood nearby.\n" +
                "\n" +
                "“I’m going to remember we were pretty close,” Romine said when asked what he would remember " +
                "about the day. “I’m not going to be in a position too many times to catch on opening day – " +
                "it’s not the gig of a backup to get those opportunities, so I was taking it in. It was fun.”";
        //</editor-fold>

        //<editor-fold desc="When">
        double probability = categoriser.getProbabilityInCategory(text, "sport");
        //</editor-fold>

        //<editor-fold desc="Then">
        assertTrue(probability > 0.9);
        //</editor-fold>
    }

    @Test
    public void firstStoryOnTheNYTimesScienceSectionIsScience() throws IOException {
        //<editor-fold desc="When">
        String text = "Coleoid cephalopods, a group encompassing octopuses, squid and cuttlefish, are the most " +
                "intelligent invertebrates: Octopuses can open jars, squid communicate with their own Morse code " +
                "and cuttlefish start learning to identify prey when they’re just embryos.\n" +
                "\n" +
                "In fact, coleoids are the only “animal lineage that has really achieved behavioral " +
                "sophistication” other than vertebrates, said Joshua Rosenthal, a senior scientist at the Marine " +
                "Biological Laboratory in Woods Hole, Mass. This sophistication could be related to a quirk in " +
                "how their genes work, according to new research from Dr. Rosenthal and Eli Eisenberg, a " +
                "biophysicist at Tel Aviv University.\n" +
                "\n" +
                "In the journal Cell on Thursday, the scientists reported that octopuses, squid and cuttlefish " +
                "make extensive use of RNA editing, a genetic process thought to have little functional " +
                "significance in most other animals, to diversify proteins in their nervous system. And natural " +
                "selection seems to have favored RNA editing in coleoids, even though it potentially slows the " +
                "DNA-based evolution that typically helps organisms acquire beneficial adaptations over time.\n" +
                "\n" +
                "Conventional wisdom says that RNA acts as a messenger, passing instructions from DNA to protein " +
                "builders in a cell.\n" +
                "\n" +
                "Continue reading the main story\n" +
                "RELATED COVERAGE\n" +
                "\n" +
                "\n" +
                "TRILOBITES\n" +
                "A Dolphin’s Recipe for Octopus APRIL 5, 2017\n" +
                "\n" +
                "MATTER\n" +
                "Scientists Find Form of Crispr Gene Editing With New Capabilities JUNE 3, 2016\n" +
                "\n" +
                "SCIENCETAKE\n" +
                "Smart Arms Control the Potential Chaos of Octopus Movement APRIL 20, 2015\n" +
                "\n" +
                "SCIENCETAKE\n" +
                "Solitary Octopuses’ Strong Statements JULY 13, 2015\n" +
                "\n" +
                "MATTER\n" +
                "For an Octopus, Seeing the Light Doesn’t Require Eyes MAY 20, 2015\n" +
                "Photo\n" +
                "\n" +
                "A longfin inshore squid. The use of RNA editing by coleoids may contribute to their behavioral " +
                "complexity. Credit Roger Hanlon/Marine Biological Laboratory\n" +
                "But sometimes, enzymes swap out some letters — the ACGU you might have learned about in school — " +
                "in the RNA’s code for others. When that happens, modified RNA can create proteins that " +
                "weren’t originally encoded in the DNA, allowing an organism to add new riffs to its base " +
                "genetic blueprint.\n" +
                "\n" +
                "This RNA editing seemed to be happening more in coleoids, so Dr. Eisenberg, Dr. Rosenthal " +
                "and Noa Liscovitch-Brauer, a postdoctoral scholar at Tel Aviv University, set out to quantify " +
                "it by looking for disagreements in the DNA and RNA sequences of two octopus, one squid and " +
                "one cuttlefish species.\n" +
                "\n" +
                "They found that coleoids have tens of thousands of so-called recoding sites, where RNA editing " +
                "results in a protein different from what was initially encoded by DNA. When they applied the " +
                "same methods to two less sophisticated mollusks — a nautilus and a sea slug — they found that " +
                "RNA editing levels were orders of magnitude lower.\n" +
                "\n" +
                "Next, the researchers compared RNA recoding sites between the octopuses, squid and cuttlefish " +
                "species and found that they shared tens of thousands of these sites to varying degrees. By " +
                "comparison, humans and mice share only about 40 recoding sites, even though they are hundreds " +
                "of millions of years closer in evolution than octopuses and squids.\n" +
                "\n" +
                "“Evolutionarily, that’s a big deal,” said Jin Billy Li, an assistant professor of genetics " +
                "at Stanford, who was not involved in this study. The findings suggest that the editing sites " +
                "are very important, he added.\n" +
                "\n" +
                "Continue reading the main story\n" +
                "Photo\n" +
                "\n" +
                "A common cuttlefish. The trade-off of heavy RNA editing is that it may slow DNA-based " +
                "evolution. Credit Roger Hanlon, Marine Biological Laboratory\n" +
                "Conserving RNA editing sites may have come with an evolutionary trade-off, however. When " +
                "the researchers looked at the coleoids’ genes, they found that DNA mutations were markedly " +
                "depleted around recoding sites to help preserve them. The result is a significant portion " +
                "of the genome “that can’t really evolve fast,” Dr. Rosenthal said.\n" +
                "\n" +
                "Slower evolution is a “big price to pay,” Dr. Eisenberg said, because DNA mutations are usually " +
                "the source of new adaptive traits. But it also suggests the greater ability to edit RNA “must " +
                "be worth it” in terms of natural selection, he said.\n" +
                "\n" +
                "He and Dr. Rosenthal found that RNA editing is enriched in coleoids’ nervous tissues, so they " +
                "suspect it contributes to these animals’ behavioral complexity, possibly by allowing for " +
                "dynamic control over proteins in response to different environmental conditions or tasks. " +
                "Previously, Dr. Rosenthal showed that RNA editing might help octopuses rapidly adapt to " +
                "temperature changes.\n" +
                "\n" +
                "Other organisms use all sorts of different methods to modify their RNA, but the possibility " +
                "that coleoids use extensive RNA editing to flexibly manipulate their nervous system is " +
                "“extraordinary,” said Kazuko Nishikura, a professor at the Wistar Institute, a nonprofit " +
                "biomedical research institute in Philadelphia, who was not involved in the study.\n" +
                "\n" +
                "“We may learn a lot from squid and octopus brains,” she said.";

        String result = getLikelyCategory(text);
        //</editor-fold>

        //<editor-fold desc="Then">
        assertEquals(result, "science");
        //</editor-fold>
    }

    @Test
    public void anAndroidStoryIsAndroid() throws IOException {
        //<editor-fold desc="Given">
        String text = "When will the next version of Android be released and what might be in it?\n" +
                "\n" +
                "A. Google released Android O, the “developer’s preview” of the next version, on March 21 so " +
                "app developers could begin working with the future operating system. The final public release " +
                "of the software and its delivery to smartphones and tablets is likely to take place this year, " +
                "but a lot of work will happen in the meantime as the system is tested and refined.\n" +
                "\n" +
                "Photo\n" +
                "\n" +
                "Google’s Android O developer’s preview allows app creators to begin working with the new " +
                "system. Credit The New York Times\n" +
                "Some of the new features expected in Android O include improved support for using the keyboard " +
                "to navigate Android apps, better control of onscreen notifications and picture-in-picture video " +
                "so people can continue to play clips while tapping into other apps. Also in the pipeline are " +
                "improved audio fidelity over Bluetooth connections and support for Wi-Fi Aware technology " +
                "(which allows compatible devices to connect directly to each other without a cellular data or " +
                "other wireless network).\n" +
                "\n" +
                "Android O will be a work in progress for several months, but Google says it will do a “deep " +
                "dive on all things Android” at its annual Google I/O conference that starts May 17. These " +
                "developer-oriented events are often used by companies to showcase new software on the way. " +
                "Microsoft’s Build conference for Windows software creators starts on May 10, and Apple’s " +
                "World Wide Developers Conference for its operating systems will take place the " +
                "first week of June.\n" +
                "\n" +
                "The release date and the official name of Android O will be revealed later in the year as " +
                "development of the system continues. The company should also announce which hardware models " +
                "will get the update. In recent years, Google has released the final versions of Android in " +
                "late summer (as with Android 7.0 Nougat, on Aug. 22, 2016) and even deep into autumn (Android " +
                "4.0 Kit Kat arrived on Oct. 31, 2013), but the day you actually get the new version depends " +
                "on your device and wireless carrier.";
        //</editor-fold>

        //<editor-fold desc="When">
        String result = getLikelyCategory(text);
        //</editor-fold>

        //<editor-fold desc="Then">
        assertEquals("android", result);
        //</editor-fold>
    }

    @Test
    @Ignore
    /**
     * Not really a test. It does the demo of categorising business and technology stories.
     */
    public void runDemo() throws IOException {
        Categoriser categoriser = new Categoriser()
                .category("business", new DirectorySampleSource("./data/samples/business"))
                .category("technology", new DirectorySampleSource("./data/samples/technology"))
                .category("sport", new DirectorySampleSource("./data/samples/sport"))
                ;

        String dataDirName = "./data/";
        String analysisDirName = dataDirName + "analysis/";
        String outputDirName = dataDirName + "output/";
        File analysisDir = new File(analysisDirName);
        for (File file : analysisDir.listFiles()) {
            String name = file.getName();
            if (name.endsWith(".txt")) {
                String analysisText = readFileAsString(file);
                String category = categoriser.getProbableCategoryFor(analysisText);
                File destDir = new File(outputDirName, category);
                if (!destDir.isDirectory()) {
                    Files.createDirectory(destDir.toPath());
                }
                Files.copy(file.toPath(), new File(destDir, name).toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    private String getLikelyCategory(String text) throws IOException {
        return new Categoriser()
                .category("sport", new DirectorySampleSource("./samples/sport"))
                .category("android", new DirectorySampleSource("./samples/android"))
                .category("science", new DirectorySampleSource("./samples/science"))
                .getProbableCategoryFor(text);
    }

    private static String readFileAsString(File filePath)
            throws java.io.IOException{
        StringBuilder fileData = new StringBuilder(1000);
        BufferedReader reader = new BufferedReader(
                new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead;
        while((numRead=reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }
        reader.close();
        return fileData.toString();
    }
}