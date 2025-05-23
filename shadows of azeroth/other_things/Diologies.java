package other_things;

import entities.Player;

public class Diologies {
    private int thrallStage = 0;
    private int smithStage = 0;


    private final String thrallFirst = "Тралл стоит у костра в Оргриммаре. Его посох пульсирует зелёным светом, а глаза полны тревоги.\n" +
            "\"Лич восстал, Пысгор. Духи предков шепчут мне: его сила растёт с каждым днём. Мы должны уничтожить его, пока не стало слишком поздно!\"\n" +
            "\n" +
            "Он бьёт посохом о землю, и искры взмывают вверх.\n" +
            "\"Но твой топор бесполезен против его ледяной плоти. Отправляйся к Громмашу Кузнецу в квартале воинов. Он знает, как усилить оружие... если, конечно, ты принесешь ему древний артефакт .\"\n" +
            "\n" +
            "Тралл кладёт руку тебе на плечо.\n" +
            "\"Иди. Духи наблюдают за тобой. \nНАЖМИТЕ 'Е' ДЛЯ ПРОДОЛЖЕНИЯ\"";
    private final String thrallSecond = "Тралл стоит у карты Нордскола. Его глаза горят золотистым светом, а в руках — твой усиленный топор.\n" +
            "\"Артефакт возвращён, Пысгор. Теперь твой топор способен нанести удар, от которого не восстанет даже Лич.\"\n" +
            "\n" +
            "Он передаёт тебе оружие, и ты чувствуешь, как магия предков струится по рукояти.\n" +
            "\"Но помни: Лич коварен. Он скрывается в Ледяной Пустоши , где-то в глубине Нордскола. Если мы не убьём его сейчас... он станет сильнее, чем когда-либо.\"\n" +
            "\n" +
            "Тралл кладёт руку на карту, указывая на ледяные пустоши.\n" +
            "\"Ступай туда. Используй силу артефакта. И помни: Орда не прощает слабости.\"\n" +
            "\n" +
            "Его голос становится твёрже.\n" +
            "\"Убей Лича. Или умри, пытаясь(иди в клетку, обозначающую .\nНАЖМИТЕ 'Е' ДЛЯ ПРОДОЛЖЕНИЯ\"";
    private final String thrallReminder = "НЕ ИСПЫТЫВАЙ МОЕ ТЕРПЕНИЕ И ПОГОВОРИ С ГРОММАШЕМ!";
    private final String thrallFinal = "Здесь больше делать нечего";

    private final String smithFirst = "Громмаш стоит у наковальни, его руки покрыты шрамами. В воздухе висит запах раскалённого металла.\n" +
            "\"Тралл прислал тебя? Ха! Не думал, что духи Лича доберутся до моей наковальни...\"\n" +
            "\n" +
            "Он указывает на пустой постамент рядом с собой.\n" +
            "\"Они украли артефакт, Пысгор. Без него я не могу вложить в твой топор силу, способную пробить ледяную плоть Лича. Артефакт спрятан в руинах за деревней . Там, где шаманы Орды видели странные огни.\"\n" +
            "\n" +
            "Громмаш достаёт из-под прилавка карту с пометками.\n" +
            "\"Вот здесь. Найди артефакт и принеси его мне. Но будь осторожен: духи Лича охраняют его. Если ты вернёшься без артефакта... даже духи не смогут тебя защитить.\"\n" +
            "\n" +
            "Он смеётся, но в его смехе слышится напряжение(иди в клетку обозначающую арку).\n" +
            "\"Иди. Орда ждёт.\nНАЖМИТЕ 'Е' ДЛЯ ПРОДОЛЖЕНИЯ\"";
    private final String smithSecond = "\"Ха! Ты вернулся... и не с пустыми руками. Дай сюда эту штуку.\"\n" +
            "\n" +
            "Он берёт артефакт и кладёт его на наковальню. Сталь твоего меча начинает светиться зелёным, а артефакт пульсирует, словно живой.\n" +
            "\"Древняя магия... не любит, когда её трогают. Но я — кузнец Орды. Я заставлю её служить нам.\"\n" +
            "\n" +
            "Громмаш бьёт молотом по артефакту, вплавляя его в клинок. Искры взлетают, а меч издаёт глухой рокот.\n" +
            "\"Готово. Теперь в твоём мече течёт кровь предков. Он рассечёт даже ледяную плоть Лича... если, конечно, ты не опозоришь Орду.\"\n" +
            "\n" +
            "Он протягивает тебе меч. Клинок теперь мерцает зелёным светом, а рукоять покрыта рунами.\n" +
            "\"Тралл ждёт тебя. Ступай. И помни: если этот меч сломается — твоя честь будет погребена вместе с ним.\"\n" +
            "\n" +
            "Громмаш возвращается к наковальне, не глядя на тебя.\n" +
            "\"Орда не прощает ошибок.\nНАЖМИТЕ 'Е' ДЛЯ ПРОДОЛЖЕНИЯ\"";
    private final String smithReminder = "\"Ты ещё здесь, Пысгор? Артефакт не вернётся сам! Или ты думаешь, духи будут ждать, пока ты почешешься?\"\n" +
            "\n" +
            "Он указывает на пустой постамент рядом с наковальней.\n" +
            "\"Три руны. Три шанса. Если духи их перехватят — Орда потеряет всё. Даже мой молот не спасёт тебя от их гнева.\"\n" +
            "\n" +
            "Кузнец хватает горсть раскалённых углей и швыряет их в кузницу. В воздухе висит запах дыма.\n" +
            "\"Ступай в руины. Найди артефакт. Или я сам отправлюсь за ним... и тогда твоё имя вычеркнут из летописей.\nНАЖМИТЕ 'Е' ДЛЯ ПРОДОЛЖЕНИЯ\"";
    private final String smithFinal = "Здесь больше нечего делать";



    public void applyTo(Diologies dialogs) {
        dialogs.setThrallStage(thrallStage);
        dialogs.setSmithStage(smithStage);
    }

    public int getThrallStage() {
        return thrallStage;
    }

    public int getSmithStage() {
        return smithStage;
    }

    public void setThrallStage(int thrallStage) {
        this.thrallStage = thrallStage;
    }

    public void setSmithStage(int smithStage) {
        this.smithStage = smithStage;
    }

    public String getThrallDialog() {
        if (thrallStage == 1 && !(smithStage == 2)) {
            return thrallReminder;
        }
        switch (thrallStage) {
            case 0: return thrallFirst;
            case 1: return thrallSecond;
            default: return thrallFinal;
        }
    }

    public String getSmithDialog(Player player) {
        if (smithStage == 1 && !player.isHasArtifact()) {
            return smithReminder;
        }
        switch (smithStage) {
            case 0: return smithFirst;
            case 1: return smithSecond;
            default: return smithFinal;
        }
    }

    public boolean canProgressThrallDialog() {

        return (thrallStage == 1 && smithStage == 2); // Переход возможен только после завершения квеста кузнеца
    }

    public void moveToNextThrallStage() {

        if (thrallStage < 2 && canProgressThrallDialog()) { // Ограничение: максимальная стадия = 2
            thrallStage++;
        }
        else if (thrallStage == 0) {
            thrallStage++;
        }

    }

    public boolean canProgressSmithStory(Player player) {
        return (smithStage == 1 && player.isHasArtifact());

    }

    public void moveToNextSmithStage(Player player) {
        if (smithStage < 2 && canProgressSmithStory(player)) {
            smithStage++;

        }
    }




    public void displayProlog() {
        System.out.println("Тысячелетиями Азерот дрожал под топотом армий, сгорал в огне магии и возрождался из пепла. Но теперь древнее зло, Лич, восстал из мертвых. Пока он слаб... но если не остановить его сейчас, он поглотит весь мир.\n" +
                "\n" +
                "Ты — воин Орды, избранный духами.\n" +
                "Ты — последняя надежда.иди к зеленому человечку (не лягушка)");
    }
}