class ANSIColorConsole {
    companion object {
        val red = "\u001B[31m"
        val reset = "\u001B[0m"
        val black = "\u001B[30m"
        val green = "\u001B[32m"
        val yellow = "\u001B[33m"
        val blue = "\u001B[34m"
        val magenta = "\u001B[35m"
        val cyan = "\u001B[36m"
        val white = "\u001B[37m"

        fun printDanger(message: String) {
            println("${red}${message}${reset}");
        }

        fun printBlack(message: String) {
            println("${black}${message}${reset}");
        }

        fun red(string: String): String{
            return "${red}${string}${reset}";
        }
        fun black(string: String): String{
            return "${black}${string}${reset}";
        }
    }
}