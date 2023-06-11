import java.util.Random;

public class Main {
    public static int bossHealth = 1500;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static String[] heroesAttackType = {"Physical", "Magical", "Mental", "Medic", "Berserk", "Thor", "Lucky", "Golem"};
    public static int[] heroesHealth = {270, 260, 250, 300, 270, 300, 200, 400};
    public static int[] heroesDamage = {10, 15, 20, 0, 15, 5, 15, 5};
    public static int roundNumber = 0;
    public static int hill = 30;

    public static void main(String[] args) {
        printStatistics();
        while (!isGameOver()) {
            playRound();
        }
    }

    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        bossHits();
        heroesHit();
        printStatistics();
        berserkShoots();
        stun();
        medicHeal();
        slope();
        golemDefence();
    }

    public static void berserkShoots() {
        Random random = new Random();
        int randomDamage = random.nextInt(15) + 1;
        int randomCritical = random.nextInt(3) + 2;
        if (heroesHealth[5] > 0 && bossHealth > 0) {
            switch (randomCritical) {
                case 1:
                    heroesDamage[5] = (heroesDamage[5] + bossDamage) - randomDamage;
                    System.out.println("Berserk Critical damage");
                    System.out.println("Losses with Increased Berserk Damage " + randomDamage);
                    break;
                case 2, 3:
                    bossDamage = 50;
                    break;
            }
        }
    }

    public static void stun() {
        Random random = new Random();
        boolean stun = random.nextBoolean();
        if (stun) {
            bossDamage = 0;
            System.out.println("The Boss is caught");
        } else {
            bossDamage = 50;
        }
    }

    public static void slope() {
        Random random = new Random();
        int randomSlope = random.nextInt(4) + 1;
        switch (randomSlope) {
            case 1:
                heroesHealth[5] = heroesHealth[5] + bossDamage;
                System.out.println("Lucky");
            case 2:
            case 3:
            case 4:
            case 5:
        }
    }

    public static void golemDefence() {
        for (int i = 0; i < heroesHealth.length - 1; i++) {
            if (heroesHealth[7] > 0 && heroesHealth[i] > 0 && heroesHealth[7] != heroesHealth[i]) {
                heroesHealth[i] += bossDamage / 5;
                heroesHealth[7] -= bossDamage / 5;
            }
        }
    }

    public static void medicHeal() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (i == 3) {
                continue;
            }
            if (heroesHealth[i] > 0 && heroesHealth[i] < 100) {
                heroesHealth[i] += 20;
            }
            System.out.println("Medic healed " + heroesAttackType[i]);
            break;
        }
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); //0,1,2
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (bossHealth > 0 && heroesHealth[i] > 0) {
                int damage = heroesDamage[i];
                if (heroesAttackType[i] == bossDefence) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                    damage = heroesDamage[i] * coeff;
                    System.out.println("Critical damage: " + damage);
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - damage;
                }
            }
        }
    }

    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        /*if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0 && heroesHealth[2] <= 0 ){
            System.out.println("Boss won!!!");
            return true;
        }
        return false;*/

        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void printStatistics() {
        /*String defence;
        if (bossDefence == null) {
            defence = "No Defence";
        } else {
            defence = bossDefence;
        }*/
        System.out.println(" ROUND: " + roundNumber + " -------------------- ");
        System.out.println("Boss health: " + bossHealth + " damage: " + bossDamage + " defence: " + (bossDefence == null ? "No Defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i] + " damage: " + heroesDamage[i]);
        }
    }
}