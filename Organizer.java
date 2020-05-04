import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Miquéias
 */
public class Organizer {

    private static JFileChooser diretorio;
    private static String path;

    private static final String DIRETORIOS[] = {
        "IMAGENS", "VIDEOS", "AUDIO", "EXCEL", "WORD", "APRESENTAÇÕES", "COMPACTADOS", "DESENVOLVIMENTO", "PROGRAMAS", "OUTROS", "PDFs"};

    private static final String[][] EXTENSOES = {
        {".jpg", ".png", ".gif", ".ico", ".psd", ".webp", ".raw", ".jpeg", ".lif", ".bmp", ".tif", ".svg", ".ai", ".webp"},
        {".mp4", ".mkv", ".avi", ".3gp", ".webm", ".mpeg", ".wmv", ".mov", ".flv"},
        {".mp3", ".flac", ".m4a", ".wav", ".aac"},
        {".xls", ".xlsx", ".xlsm", ".xlsb", ".xltx", ".xltm", ".xla", ".xlam", ".csv"},
        {".doc", ".docx", ".docm", ".dotx", ".dot", ".dotm"},
        {".pptx", ".pptm", ".ppt", ".potx", ".potm", ".pot", ".thmx", ".ppsx", ".ppsm", ".pps", ".odp"},
        {".iso", ".zip", ".rar", ".7z", ".tgz", ".tar", ".bz"},
        {".py", ".c", ".cpp", ".dart", ".js", ".bat", ".sh", ".o", ".class", ".java", ".php", ".css", ".js", ".dfm", ".pas", ".erwin", ".sql"},
        {".exe", ".msi"},
        {".ini", ".lnk", ".url", ".txt", ".torrent", ".md"},
        {".pdf"}};

    public static void main(String[] args) throws InterruptedException {
        Organizer.informacoes();
        Thread.sleep(2000);
        diretorio = new JFileChooser("");//cria um seletor de diretorio
        diretorio.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        if (diretorio.showOpenDialog(diretorio) == JFileChooser.APPROVE_OPTION) {
            path = diretorio.getSelectedFile().getAbsolutePath()+"\\";
            System.out.println("Caminho  : " + path);
            System.out.println("Diretório: " + diretorio.getSelectedFile().getName());
            int option = JOptionPane.showConfirmDialog(null, "Deseja apagar diretórios vazios do caminho\n"
                    + diretorio.getSelectedFile().getAbsolutePath() + " ?");
            if (option == 0) {
                limparDiretorio(path);
            }

            File diretorio = new File(path);
            File[] files = diretorio.listFiles();
            File dir = null;

            for (File file : files) {
                if (file.isFile()) {
                    String extensaoArquivo = getFileExtension(file).toLowerCase();
                    for (int extI = 0; extI < EXTENSOES.length; extI++) {
                        for (String item : EXTENSOES[extI]) {
                            if (extensaoArquivo.equals(item.toLowerCase())) {
                                
                                dir = new File(path + DIRETORIOS[extI]);
                                if (!dir.exists()) {
                                    dir.mkdirs();
                                }
                                file.renameTo(new File(dir + "\\", file.getName().toLowerCase()));
                            }
                        }
                    }
                }
            }

            JOptionPane.showMessageDialog(null, "Finalizado!");
            System.exit(0);
        }

    }

    private static void limparDiretorio(String path) {

        File diretorioPrincipal = new File(path);
        File[] files = diretorioPrincipal.listFiles();
        File[] auxFiles = null;

        for (File file : files) {

            if (file.isDirectory()) {
                auxFiles = file.listFiles();
                
                if (auxFiles != null && auxFiles.length == 0 ) {
                    file.delete();
                }
            }
        }
    }

    private static void informacoes() {
        JOptionPane.showMessageDialog(null,"OBSERVAÇÕES\n"
                + "1 -> O PROGRAMA RODA EM MODO DE COMPATIBILIDADE, SENDO ASSIM, TODOS OS ARQUIVOS SERÃO MOVIDOS\n"
                + "COM SEUS RESPECTIVOS NOMES EM CAIXA BAIXA(EM MINUSCULO).\n"
                + "\n2 -> NÃO SERA POSSÍVEL REVERTER AS ALTERAÇÕES APÓS A EXECUÇÃO DO PROGRAMA\n"
                + "\n3 -> USE COM CAUTELA, DE PREFERÊNCIA EM PASTAS QUE VOCE SAIBA QUE NÃO TENHA INFORMACOES IMPORTANTES");
    }


    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf("."));
            //return fileName.substring(fileName.lastIndexOf(".")+1);//descomentar para retornar a extensão sem o '.' (ponto) e comentar a linha anterior
        } else {
            return "";
        }
    }
}
