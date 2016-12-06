package com.example.android.farliggodtapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.farliggodtapp.taxon.Specie;

/**
 * Created by Agne Ødegaard on 06/10/16.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String databaseName = "FarligGodt.db";
    private static final String tableName = "saved";

    private static final String tableColValue = "value";
    private static final String tableColType = "type";


    public DatabaseHelper(Context context) {
        super(context, databaseName, null, 1);
    }

    /**
     * When the database is created
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + tableName + " (" + tableColValue + " VARCHAR(255), " + tableColType + " VARCHAR(255) PRIMARY KEY UNIQUE)");

        db.execSQL("CREATE TABLE `blacklist` (" +
                "  `id` int(11) NOT NULL," +
                "  `scientificName` varchar(255) NOT NULL," +
                "  `navn` varchar(255) NOT NULL," +
                "  `svalbard` tinyint(1) NOT NULL DEFAULT '0'," +
                "  `risiko` varchar(255) NOT NULL," +
                "  `taxonID` int(11) NOT NULL," +
                "  `canEat` tinyint(1) NOT NULL DEFAULT '0'," +
                "  `family` varchar(255) NOT NULL," +
                "  `image` int(11) NOT NULL" +
                ")");


        db.execSQL("INSERT INTO `blacklist` (`id`, `scientificName`, `navn`, `svalbard`, `risiko`, `taxonID`, `canEat`, `family`, `image`) VALUES\n" +
                "(1, 'Abies alba', 'edelgran', 0, 'HI - Høy risiko', 63753, 0, 'groupname', 1),\n" +
                "(2, 'Acartia tonsa', '', 0, 'HI - Høy risiko', 85402, 0, 'groupname', 1),\n" +
                "(3, 'Agaricus subperonatus', 'hagesjampinjong', 0, 'HI - Høy risiko', 34779, 0, 'groupname', 1),\n" +
                "(4, 'Agrilus anxius', 'amerikansk bjørkepraktbille', 0, 'HI - Høy risiko', 84372, 0, 'groupname', 1),\n" +
                "(5, 'Agrilus planipennis', 'asiatisk askepraktbille', 0, 'HI - Høy risiko', 79367, 0, 'groupname', 1),\n" +
                "(6, 'Alchemilla mollis', 'praktmarikåpe', 0, 'HI - Høy risiko', 63193, 0, 'groupname', 1),\n" +
                "(7, 'Alopochen aegyptiaca', 'niland', 0, 'HI - Høy risiko', 3413, 0, 'groupname', 11),\n" +
                "(8, 'Amphibalanus amphitrite', '', 0, 'HI - Høy risiko', 86686, 0, 'groupname', 1),\n" +
                "(9, 'Amphibalanus improvisus', 'brakkvannsrur', 0, 'HI - Høy risiko', 86687, 0, 'groupname', 1),\n" +
                "(10, 'Anthriscus sylvestris', 'hundekjeks', 1, 'HI - Høy risiko', 60232, 0, 'groupname', 1),\n" +
                "(11, 'Anthyllis vulneraria carpatica', 'fÙrrundbelg', 0, 'HI - Høy risiko', 61852, 0, 'groupname', 1),\n" +
                "(12, 'Aphidoletes abietis', '', 0, 'HI - Høy risiko', 84627, 0, 'groupname', 1),\n" +
                "(13, 'Arion rufus', 'rødskogsnegl', 0, 'HI - Høy risiko', 79721, 0, 'groupname', 1),\n" +
                "(14, 'Aronia ◊prunifolia', 'purpursurbær', 0, 'HI - Høy risiko', 63221, 0, 'groupname', 1),\n" +
                "(15, 'Aruncus dioicus', 'skogskjegg', 0, 'HI - Høy risiko', 63223, 0, 'groupname', 1),\n" +
                "(16, 'Bemisia tabaci', 'bomullsmellus', 0, 'HI - Høy risiko', 85516, 0, 'groupname', 1),\n" +
                "(17, 'Bergenia cordifolia', 'hjertebergblom', 0, 'HI - Høy risiko', 63589, 0, 'groupname', 1),\n" +
                "(18, 'Bonnemaisonia hamifera', 'krokbærer ', 0, 'HI - Høy risiko', 66319, 0, 'groupname', 1),\n" +
                "(19, 'Bromopsis inermis', 'bladfaks', 0, 'HI - Høy risiko', 59895, 0, 'groupname', 1),\n" +
                "(20, 'Bunias orientalis', 'russekål', 0, 'HI - Høy risiko', 61090, 0, 'groupname', 1),\n" +
                "(21, 'Camelostrongylus mentulatus', '', 0, 'HI - Høy risiko', 85780, 0, 'groupname', 1),\n" +
                "(22, 'Campanula glomerata', 'prakttoppklokke', 0, 'HI - Høy risiko', 79967, 0, 'groupname', 1),\n" +
                "(23, 'Campylopus introflexus', 'ribbesåtemose', 0, 'HI - Høy risiko', 64203, 0, 'groupname', 1),\n" +
                "(24, 'Caragana arborescens', 'sibirertebusk', 0, 'HI - Høy risiko', 61867, 0, 'groupname', 1),\n" +
                "(25, 'Carassius auratus', 'gullfisk', 0, 'HI - Høy risiko', 26114, 0, 'groupname', 1),\n" +
                "(26, 'Celastrus orbicularis', '', 0, 'HI - Høy risiko', 84786, 0, 'groupname', 1),\n" +
                "(27, 'Celtodoryx ciocalyptoides', '', 0, 'HI - Høy risiko', 85996, 0, 'groupname', 1),\n" +
                "(28, 'Centaurea montana', 'honningknoppurt', 0, 'HI - Høy risiko', 60450, 0, 'groupname', 1),\n" +
                "(29, 'Centaurea nigra nemoralis', 'ballastknoppurt', 0, 'HI - Høy risiko', 60452, 0, 'groupname', 1),\n" +
                "(30, 'Cicerbita macrophylla', '', 0, 'HI - Høy risiko', 60465, 0, 'groupname', 1),\n" +
                "(31, 'Cicerbita plumieri', 'alpeturt', 0, 'HI - Høy risiko', 60467, 0, 'groupname', 1),\n" +
                "(32, 'Codium fragile', 'pollpryd', 0, 'HI - Høy risiko', 65924, 0, 'groupname', 1),\n" +
                "(33, 'Corella eumyota', '', 0, 'HI - Høy risiko', 85389, 0, 'groupname', 1),\n" +
                "(34, 'Cotoneaster moupinensis', 'mørkmispel', 0, 'HI - Høy risiko', 63239, 0, 'groupname', 1),\n" +
                "(35, 'Cotoneaster tomentosus', 'filtmispel', 0, 'HI - Høy risiko', 63246, 0, 'groupname', 1),\n" +
                "(36, 'Crataegus laevigata', 'parkhagtorn', 0, 'HI - Høy risiko', 63251, 0, 'groupname', 1),\n" +
                "(37, 'Crataegus sanguinea', 'sibirhagtorn', 0, 'HI - Høy risiko', 63258, 0, 'groupname', 1),\n" +
                "(38, 'Crepidula fornicata', '', 0, 'HI - Høy risiko', 80004, 0, 'groupname', 1),\n" +
                "(39, 'Crepis biennis', 'veihaukeskjegg', 0, 'HI - Høy risiko', 60496, 0, 'groupname', 1),\n" +
                "(40, 'Cronartium ribicola', 'solbærfiltrust', 0, 'HI - Høy risiko', 80690, 0, 'groupname', 1),\n" +
                "(41, 'Culex pipiens', 'liten husmygg', 0, 'HI - Høy risiko', 18628, 0, 'groupname', 1),\n" +
                "(42, 'Dama dama', 'dåhjort', 0, 'HI - Høy risiko', 31255, 0, 'groupname', 1),\n" +
                "(43, 'Daphnia ambigua', '', 0, 'HI - Høy risiko', 85547, 0, 'groupname', 1),\n" +
                "(44, 'Daphnia parvula', '', 0, 'HI - Høy risiko', 85548, 0, 'groupname', 1),\n" +
                "(45, 'Doronicum macrophyllum', 'kjempegullkurv', 0, 'HI - Høy risiko', 60510, 0, 'groupname', 1),\n" +
                "(46, 'Dreissena bugensis', '', 0, 'HI - Høy risiko', 86548, 0, 'groupname', 1),\n" +
                "(47, 'Echinops sphaerocephalus', 'kuletistel', 0, 'HI - Høy risiko', 60518, 0, 'groupname', 1),\n" +
                "(48, 'Elminius modestus', '', 0, 'HI - Høy risiko', 85989, 0, 'groupname', 1),\n" +
                "(49, 'Erysiphe hypophylla', '', 0, 'HI - Høy risiko', 85430, 0, 'groupname', 1),\n" +
                "(50, 'Festuca ovina capillata', 'grannsvingel', 0, 'HI - Høy risiko', 60004, 0, 'groupname', 1),\n" +
                "(51, 'Festuca rubra megastachys', 'engrødsvingel', 0, 'HI - Høy risiko', 60010, 0, 'groupname', 1),\n" +
                "(52, 'Filipendula kamtschatica', 'kjempemjødurt', 0, 'HI - Høy risiko', 63267, 0, 'groupname', 1),\n" +
                "(53, 'Fragaria moschata', 'moskusjordbær', 0, 'HI - Høy risiko', 63275, 0, 'groupname', 1),\n" +
                "(54, 'Geum aleppicum', 'russehumleblom', 0, 'HI - Høy risiko', 63280, 0, 'groupname', 1),\n" +
                "(55, 'Geum quellyon', 'chilehumleblom', 0, 'HI - Høy risiko', 63282, 0, 'groupname', 1),\n" +
                "(56, 'Glomerella acutata', 'jordbærsvartflekk', 0, 'HI - Høy risiko', 85846, 0, 'groupname', 1),\n" +
                "(57, 'Glyceria grandis', 'møllesøtgras', 0, 'HI - Høy risiko', 60019, 0, 'groupname', 1),\n" +
                "(58, 'Glyceria maxima', 'kjempesøtgras', 0, 'HI - Høy risiko', 60021, 0, 'groupname', 1),\n" +
                "(59, 'Gobio gobio', 'sandkryper', 0, 'HI - Høy risiko', 26140, 0, 'groupname', 1),\n" +
                "(60, 'Halyomorpha halys', '', 0, 'HI - Høy risiko', 86693, 0, 'groupname', 1),\n" +
                "(61, 'Helix pomatia', 'vinbergsnegl', 0, 'HI - Høy risiko', 79768, 0, 'groupname', 1),\n" +
                "(62, 'Hemigrapsus sanguineus', '', 0, 'HI - Høy risiko', 85887, 0, 'groupname', 1),\n" +
                "(63, 'Hemigrapsus takanoi', '', 0, 'HI - Høy risiko', 85888, 0, 'groupname', 1),\n" +
                "(64, 'Ips amitinus', '', 0, 'HI - Høy risiko', 9104, 0, 'groupname', 1),\n" +
                "(65, 'Ips cembrae', '', 0, 'HI - Høy risiko', 9105, 0, 'groupname', 1),\n" +
                "(66, 'Ips subelongatus', '', 0, 'HI - Høy risiko', 85692, 0, 'groupname', 1),\n" +
                "(67, 'Lepomis gibbosus', 'rødgjellet solabbor', 0, 'HI - Høy risiko', 26436, 0, 'groupname', 1),\n" +
                "(68, 'Leptoglossus occidentalis', '', 0, 'HI - Høy risiko', 79825, 0, 'groupname', 1),\n" +
                "(69, 'Leucaspius delineatus', 'regnlaue', 0, 'HI - Høy risiko', 26126, 0, 'groupname', 1),\n" +
                "(70, 'Limax maximus', 'boakjølsnegl', 0, 'HI - Høy risiko', 79704, 0, 'groupname', 1),\n" +
                "(71, 'Linepithema humile', '', 0, 'HI - Høy risiko', 77422, 0, 'groupname', 1),\n" +
                "(72, 'Lithocharis nigriceps', '', 0, 'HI - Høy risiko', 12431, 0, 'groupname', 1),\n" +
                "(73, 'Lonicera caprifolium', 'kaprifol', 0, 'HI - Høy risiko', 61673, 0, 'groupname', 1),\n" +
                "(74, 'Lonicera involucrata', 'skjermleddved', 0, 'HI - Høy risiko', 61674, 0, 'groupname', 1),\n" +
                "(75, 'Lonicera tatarica', 'tatarleddved', 0, 'HI - Høy risiko', 61678, 0, 'groupname', 1),\n" +
                "(76, 'Lycium barbarum', 'bukketorn', 0, 'HI - Høy risiko', 63683, 0, 'groupname', 1),\n" +
                "(77, 'Lysimachia nummularia', 'krypfredløs', 0, 'HI - Høy risiko', 61803, 0, 'groupname', 1),\n" +
                "(78, 'Lysimachia punctata', 'fagerfredløs', 0, 'HI - Høy risiko', 61804, 0, 'groupname', 1),\n" +
                "(79, 'Mahonia aquifolium', 'mahonie', 0, 'HI - Høy risiko', 62963, 0, 'groupname', 1),\n" +
                "(80, 'Marenzelleria neglecta', '', 0, 'HI - Høy risiko', 85844, 0, 'groupname', 1),\n" +
                "(81, 'Marenzelleria viridis', '', 0, 'HI - Høy risiko', 85843, 0, 'groupname', 1),\n" +
                "(82, 'Melilotus altissimus', 'strandsteinkløver', 0, 'HI - Høy risiko', 61958, 0, 'groupname', 1),\n" +
                "(83, 'Meloidogyne chitwoodi', '', 0, 'HI - Høy risiko', 85770, 0, 'groupname', 1),\n" +
                "(84, 'Meloidogyne fallax', '', 0, 'HI - Høy risiko', 85771, 0, 'groupname', 1),\n" +
                "(85, 'Meloidogyne hapla', '', 0, 'HI - Høy risiko', 85772, 0, 'groupname', 1),\n" +
                "(86, 'Meloidogyne minor', '', 0, 'HI - Høy risiko', 85774, 0, 'groupname', 1),\n" +
                "(87, 'Meloidogyne naasi', '', 0, 'HI - Høy risiko', 85773, 0, 'groupname', 1),\n" +
                "(88, 'Mutinus ravenelii', 'hagestanksopp', 0, 'HI - Høy risiko', 39594, 0, 'groupname', 1),\n" +
                "(89, 'Nematodirus battus', '', 0, 'HI - Høy risiko', 85777, 0, 'groupname', 1),\n" +
                "(90, 'Neogobius melanostomus', 'svartmunnet kutling', 0, 'HI - Høy risiko', 84957, 0, 'groupname', 1),\n" +
                "(91, 'Nymphoides peltata', 'sjøgull', 0, 'HI - Høy risiko', 60931, 0, 'groupname', 1),\n" +
                "(92, 'Ocenebra inornata', '', 0, 'HI - Høy risiko', 85820, 0, 'groupname', 1),\n" +
                "(93, 'Oncorhynchus gorbuscha', 'pukkellaks', 0, 'HI - Høy risiko', 26172, 0, 'groupname', 1),\n" +
                "(94, 'Oxychilus draparnaudi', 'storglanssnegl', 0, 'HI - Høy risiko', 79670, 0, 'groupname', 1),\n" +
                "(95, 'Palaemon macrodactylus', '', 0, 'HI - Høy risiko', 85397, 0, 'groupname', 1),\n" +
                "(96, 'Petasites hybridus', 'legepestrot', 0, 'HI - Høy risiko', 60724, 0, 'groupname', 1),\n" +
                "(97, 'Petasites japonicus', '', 0, 'HI - Høy risiko', 60725, 0, 'groupname', 1),\n" +
                "(98, 'Phaeolepiota aurea', 'gullskjellsopp', 0, 'HI - Høy risiko', 37980, 0, 'groupname', 1),\n" +
                "(99, 'Phytophthora cambivora', '', 0, 'HI - Høy risiko', 80533, 0, 'groupname', 1),\n" +
                "(100, 'Phytophthora gonapodyides', '', 0, 'HI - Høy risiko', 84999, 0, 'groupname', 1),\n" +
                "(101, 'Phytophthora megasperma', '', 0, 'HI - Høy risiko', 80537, 0, 'groupname', 1),\n" +
                "(102, 'Phytophthora syringae', '', 0, 'HI - Høy risiko', 85000, 0, 'groupname', 1),\n" +
                "(103, 'Picea glauca', 'hvitgran', 0, 'HI - Høy risiko', 63771, 0, 'groupname', 1),\n" +
                "(104, 'Pinus peuce', 'silkefuru', 0, 'HI - Høy risiko', 63782, 0, 'groupname', 1),\n" +
                "(105, 'Potamopyrgus antipodarum', 'vandrepollsnegl', 0, 'HI - Høy risiko', 83708, 0, 'groupname', 1),\n" +
                "(106, 'Prunus cerasifera', 'kirsebærplomme', 0, 'HI - Høy risiko', 63329, 0, 'groupname', 1),\n" +
                "(107, 'Prunus cerasus', 'kirsebær', 0, 'HI - Høy risiko', 63330, 0, 'groupname', 1),\n" +
                "(108, 'Prunus serotina', 'romhegg', 0, 'HI - Høy risiko', 63339, 0, 'groupname', 1),\n" +
                "(109, 'Pseudodactylogyrus anguillae', '', 0, 'HI - Høy risiko', 85424, 0, 'groupname', 1),\n" +
                "(110, 'Pseudodactylogyrus bini', '', 0, 'HI - Høy risiko', 85425, 0, 'groupname', 1),\n" +
                "(111, 'Rana kl. esculenta', 'hybridfrosk', 0, 'HI - Høy risiko', 78306, 0, 'groupname', 1),\n" +
                "(112, 'Rana ridibunda', 'latterfrosk', 0, 'HI - Høy risiko', 1579, 0, 'groupname', 1),\n" +
                "(113, 'Robinia pseudacacia', 'robinia', 0, 'HI - Høy risiko', 61989, 0, 'groupname', 1),\n" +
                "(114, 'Rubus armeniacus', 'armÈnbjørnebær', 0, 'HI - Høy risiko', 63371, 0, 'groupname', 1),\n" +
                "(115, 'Rumex pseudoalpinus', 'alpehøymol', 0, 'HI - Høy risiko', 62945, 0, 'groupname', 1),\n" +
                "(116, 'Salix ◊meyeriana', 'blankpil', 0, 'HI - Høy risiko', 62642, 0, 'groupname', 1),\n" +
                "(117, 'Salvelinus namaycush', 'canadarøye', 0, 'HI - Høy risiko', 85449, 0, 'groupname', 1),\n" +
                "(118, 'Sambucus racemosa', 'rødhyll', 0, 'HI - Høy risiko', 61663, 0, 'groupname', 1),\n" +
                "(119, 'Senecio inaequidens', 'boersvineblom', 0, 'HI - Høy risiko', 60756, 0, 'groupname', 1),\n" +
                "(120, 'Senecio viscosus', 'klistersvineblom', 0, 'HI - Høy risiko', 60765, 0, 'groupname', 1),\n" +
                "(121, 'Solidago gigantea', '', 0, 'HI - Høy risiko', 60777, 0, 'groupname', 1),\n" +
                "(122, 'Sorbaria sorbifolia', 'rognspirea', 0, 'HI - Høy risiko', 63440, 0, 'groupname', 1),\n" +
                "(123, 'Spiraea ◊rosalba', 'purpurspirea', 0, 'HI - Høy risiko', 63474, 0, 'groupname', 1),\n" +
                "(124, 'Spiraea ◊rubella', 'bleikspirea', 0, 'HI - Høy risiko', 63475, 0, 'groupname', 1),\n" +
                "(125, 'Stratiotes aloides', 'vassaloe', 0, 'HI - Høy risiko', 59301, 0, 'groupname', 1),\n" +
                "(126, 'Styela clava', 'lærsekkdyr', 0, 'HI - Høy risiko', 78584, 0, 'groupname', 1),\n" +
                "(127, 'Swida alba', 'sibirkornell', 0, 'HI - Høy risiko', 61630, 0, 'groupname', 1),\n" +
                "(128, 'Symphytum officinale', 'valurt', 0, 'HI - Høy risiko', 61018, 0, 'groupname', 1),\n" +
                "(129, 'Symphytum ◊uplandicum', 'mellomvalurt', 0, 'HI - Høy risiko', 61020, 0, 'groupname', 1),\n" +
                "(130, 'Syringa vulgaris', 'syrin', 0, 'HI - Høy risiko', 62376, 0, 'groupname', 1),\n" +
                "(131, 'Taphrina ulmi', 'almeblære', 0, 'HI - Høy risiko', 59254, 0, 'groupname', 1),\n" +
                "(132, 'Ulex europaeus', 'gulltorn', 0, 'HI - Høy risiko', 62044, 0, 'groupname', 1),\n" +
                "(133, 'Viola ◊wittrockiana', 'hagestemorsblom', 0, 'HI - Høy risiko', 62773, 0, 'groupname', 1),\n" +
                "(134, 'Watersipora subtorquata', '', 0, 'HI - Høy risiko', 85994, 0, 'groupname', 1),\n" +
                "(135, 'Acer pseudoplatanus', 'platanlønn', 0, 'SE - Svært høy risiko', 63520, 0, 'groupname', 1),\n" +
                "(136, 'Achillea nobilis', 'engryllik', 0, 'SE - Svært høy risiko', 60355, 0, 'groupname', 1),\n" +
                "(137, 'Allium schoenoprasum schoenoprasum', 'matgrasløk', 0, 'SE - Svært høy risiko', 59373, 0, 'groupname', 3),\n" +
                "(138, 'Amelanchier alnifolia', 'taggblåhegg', 0, 'SE - Svært høy risiko', 63210, 0, 'groupname', 1),\n" +
                "(139, 'Amelanchier lamarckii', 'kanadablåhegg', 0, 'SE - Svært høy risiko', 63212, 0, 'groupname', 1),\n" +
                "(140, 'Amelanchier spicata', 'blåhegg', 0, 'SE - Svært høy risiko', 63214, 0, 'groupname', 1),\n" +
                "(141, 'Angiostrongylus vasorum', '', 0, 'SE - Svært høy risiko', 85782, 0, 'groupname', 1),\n" +
                "(142, 'Anguillicoloides crassus', '', 0, 'SE - Svært høy risiko', 85418, 0, 'groupname', 1),\n" +
                "(143, 'Arabis caucasica', 'hageskrinneblom', 0, 'SE - Svært høy risiko', 61047, 0, 'groupname', 1),\n" +
                "(144, 'Arctium tomentosum', 'ullborre', 0, 'SE - Svært høy risiko', 60383, 0, 'groupname', 1),\n" +
                "(145, 'Arion vulgaris', 'brunskogsnegl', 0, 'SE - Svært høy risiko', 79844, 0, 'groupname', 1),\n" +
                "(146, 'Barbarea vulgaris', 'vinterkarse', 0, 'SE - Svært høy risiko', 61063, 0, 'groupname', 1),\n" +
                "(147, 'Berberis thunbergii', 'høstberberis', 0, 'SE - Svært høy risiko', 62955, 0, 'groupname', 1),\n" +
                "(148, 'Branta canadensis', 'kanadagås', 0, 'SE - Svært høy risiko', 3457, 0, 'groupname', 2),\n" +
                "(149, 'Bursaphelenchus xylophilus', 'furuvednematode', 0, 'SE - Svært høy risiko', 85358, 0, 'groupname', 1),\n" +
                "(150, 'Calystegia sepium spectabilis', 'prydstrandvindel', 0, 'SE - Svært høy risiko', 63649, 0, 'groupname', 1),\n" +
                "(151, 'Campanula latifolia macrantha', 'prydstorklokke', 0, 'SE - Svært høy risiko', 60896, 0, 'groupname', 1),\n" +
                "(152, 'Caprella mutica', 'skjellettkresp', 0, 'SE - Svært høy risiko', 1784, 0, 'groupname', 1),\n" +
                "(153, 'Cerastium tomentosum', 'filtarve', 0, 'SE - Svært høy risiko', 61439, 0, 'groupname', 1),\n" +
                "(154, 'Cercopagis pengoi', '', 0, 'SE - Svært høy risiko', 85545, 0, 'groupname', 1),\n" +
                "(155, 'Chionoecetes opilio', 'snøkrabbe', 0, 'SE - Svært høy risiko', 14395, 0, 'groupname', 1),\n" +
                "(156, 'Clematis alpina', 'alperanke', 0, 'SE - Svært høy risiko', 63061, 0, 'groupname', 1),\n" +
                "(157, 'Corydalis solida', 'hagelerkespore', 0, 'SE - Svært høy risiko', 62977, 0, 'groupname', 1),\n" +
                "(158, 'Cotoneaster bullatus', 'bulkemispel', 0, 'SE - Svært høy risiko', 63230, 0, 'groupname', 1),\n" +
                "(159, 'Cotoneaster dielsianus', 'dielsmispel', 0, 'SE - Svært høy risiko', 63232, 0, 'groupname', 1),\n" +
                "(160, 'Cotoneaster divaricatus', 'sprikemispel', 0, 'SE - Svært høy risiko', 63233, 0, 'groupname', 1),\n" +
                "(161, 'Cotoneaster horizontalis', 'krypmispel', 0, 'SE - Svært høy risiko', 63235, 0, 'groupname', 1),\n" +
                "(162, 'Cotoneaster lucidus', 'blankmispel', 0, 'SE - Svært høy risiko', 63238, 0, 'groupname', 1),\n" +
                "(163, 'Cotoneaster multiflorus', 'blomstermispel', 0, 'SE - Svært høy risiko', 63240, 0, 'groupname', 1),\n" +
                "(164, 'Cotoneaster salicifolius', 'pilemispel', 0, 'SE - Svært høy risiko', 63244, 0, 'groupname', 1),\n" +
                "(165, 'Crassostrea gigas', 'stillehavsøsters', 0, 'SE - Svært høy risiko', 84141, 0, 'groupname', 12),\n" +
                "(166, 'Deraeocoris lutescens', '', 0, 'SE - Svært høy risiko', 68984, 0, 'groupname', 1),\n" +
                "(167, 'Didemnum vexillum', '', 0, 'SE - Svært høy risiko', 83777, 0, 'groupname', 1),\n" +
                "(168, 'Dreissena polymorpha', 'sebramusling', 0, 'SE - Svært høy risiko', 85828, 0, 'groupname', 1),\n" +
                "(169, 'Echinococcus multilocularis', '', 0, 'SE - Svært høy risiko', 85790, 0, 'groupname', 1),\n" +
                "(170, 'Elodea canadensis', 'vasspest', 0, 'SE - Svært høy risiko', 59293, 0, 'groupname', 1),\n" +
                "(171, 'Elodea nuttallii', 'smal vasspest', 0, 'SE - Svært høy risiko', 59294, 0, 'groupname', 1),\n" +
                "(172, 'Ensis directus', 'amerikaknivskjell', 0, 'SE - Svært høy risiko', 86683, 0, 'groupname', 1),\n" +
                "(173, 'Epilobium ciliatum ciliatum', 'ugrasmjølke', 0, 'SE - Svært høy risiko', 62797, 0, 'groupname', 1),\n" +
                "(174, 'Epilobium ciliatum glandulosum', 'alaskamjølke', 0, 'SE - Svært høy risiko', 62798, 0, 'groupname', 1),\n" +
                "(175, 'Eriocheir sinensis', 'kinaullhåndskrabbe', 0, 'SE - Svært høy risiko', 14465, 0, 'groupname', 1),\n" +
                "(176, 'Erysiphe alphitoides', 'eikemeldugg', 0, 'SE - Svært høy risiko', 85428, 0, 'groupname', 1),\n" +
                "(177, 'Festuca rubra commutata', 'veirødsvingel', 0, 'SE - Svært høy risiko', 60009, 0, 'groupname', 1),\n" +
                "(178, 'Gammarus tigrinus', '', 0, 'SE - Svært høy risiko', 84947, 0, 'groupname', 1),\n" +
                "(179, 'Geum macrophyllum', 'amerikahumleblom', 0, 'SE - Svært høy risiko', 63281, 0, 'groupname', 1),\n" +
                "(180, 'Gyrodactylus salaris', '', 0, 'SE - Svært høy risiko', 84556, 0, 'groupname', 1),\n" +
                "(181, 'Harmonia axyridis', 'harlekinmarihøne', 0, 'SE - Svært høy risiko', 7359, 0, 'groupname', 1),\n" +
                "(182, 'Heracleum mantegazzianum', 'kjempebjørnekjeks', 0, 'SE - Svært høy risiko', 60287, 0, 'groupname', 1),\n" +
                "(183, 'Heracleum persicum', 'tromsøpalme', 0, 'SE - Svært høy risiko', 60288, 0, 'groupname', 1),\n" +
                "(184, 'Heterosiphonia japonica', '', 0, 'SE - Svært høy risiko', 79926, 0, 'groupname', 1),\n" +
                "(185, 'Homarus americanus', 'amerikahummer', 0, 'SE - Svært høy risiko', 14309, 0, 'groupname', 1),\n" +
                "(186, 'Hymenoscyphus pseudoalbidus', 'askeskuddbeger', 0, 'SE - Svært høy risiko', 85811, 0, 'groupname', 1),\n" +
                "(187, 'Impatiens glandulifera', 'kjempespringfrø', 0, 'SE - Svært høy risiko', 61721, 0, 'groupname', 1),\n" +
                "(188, 'Impatiens parviflora', 'mongolspringfrø', 0, 'SE - Svært høy risiko', 61723, 0, 'groupname', 1),\n" +
                "(189, 'Laburnum alpinum', 'alpegullregn', 0, 'SE - Svært høy risiko', 61898, 0, 'groupname', 1),\n" +
                "(190, 'Laburnum anagyroides', 'gullregn', 0, 'SE - Svært høy risiko', 61899, 0, 'groupname', 1),\n" +
                "(191, 'Lamiastrum galeobdolon galeobdolon', 'parkgulltvetann', 0, 'SE - Svært høy risiko', 62265, 0, 'groupname', 1),\n" +
                "(192, 'Larix decidua', 'europalerk', 0, 'SE - Svært høy risiko', 63762, 0, 'groupname', 1),\n" +
                "(193, 'Lepidium latifolium', 'strandkarse', 0, 'SE - Svært høy risiko', 61212, 0, 'groupname', 13),\n" +
                "(194, 'Lepus europaeus', 'sørhare', 0, 'SE - Svært høy risiko', 31106, 0, 'groupname', 6),\n" +
                "(195, 'Linaria repens', 'stripetorskemunn', 0, 'SE - Svært høy risiko', 62479, 0, 'groupname', 1),\n" +
                "(196, 'Lonicera caerulea', 'blåleddved', 0, 'SE - Svært høy risiko', 61672, 0, 'groupname', 1),\n" +
                "(197, 'Lotus corniculatus sativus', 'veitiriltunge', 0, 'SE - Svært høy risiko', 61932, 0, 'groupname', 1),\n" +
                "(198, 'Lupinus nootkatensis', 'sandlupin', 0, 'SE - Svært høy risiko', 61940, 0, 'groupname', 1),\n" +
                "(199, 'Lupinus perennis', 'jærlupin', 0, 'SE - Svært høy risiko', 61941, 0, 'groupname', 1),\n" +
                "(200, 'Lupinus polyphyllus', 'hagelupin', 0, 'SE - Svært høy risiko', 61942, 0, 'groupname', 1),\n" +
                "(201, 'Malus domestica', 'eple', 0, 'SE - Svært høy risiko', 63289, 0, 'groupname', 1),\n" +
                "(202, 'Melampsoridium hiratsukanum', 'orerust', 0, 'SE - Svært høy risiko', 81032, 0, 'groupname', 1),\n" +
                "(203, 'Melilotus albus', 'hvitsteinkløver', 0, 'SE - Svært høy risiko', 61957, 0, 'groupname', 1),\n" +
                "(204, 'Mnemiopsis leidyi', '', 0, 'SE - Svært høy risiko', 14097, 0, 'groupname', 1),\n" +
                "(205, 'Mycosphaerella pini', '', 0, 'SE - Svært høy risiko', 87973, 0, 'groupname', 1),\n" +
                "(206, 'Myrrhis odorata', 'spansk kjørvel', 0, 'SE - Svært høy risiko', 60303, 0, 'groupname', 7),\n" +
                "(207, 'Neovison vison', 'mink', 0, 'SE - Svært høy risiko', 31227, 0, 'groupname', 10),\n" +
                "(208, 'Nyctereutes procyonoides', 'mårhund', 0, 'SE - Svært høy risiko', 31172, 0, 'groupname', 1),\n" +
                "(209, 'Odontites vernus serotinus', 'engrødtopp', 0, 'SE - Svært høy risiko', 62404, 0, 'groupname', 1),\n" +
                "(210, 'Oncorhynchus mykiss', 'regnbueørret', 0, 'SE - Svært høy risiko', 26171, 0, 'groupname', 15),\n" +
                "(211, 'Ondatra zibethicus', 'bisam', 0, 'SE - Svært høy risiko', 31083, 0, 'groupname', 1),\n" +
                "(212, 'Ophiostoma novo-ulmi', 'almesykesopp', 0, 'SE - Svært høy risiko', 85005, 0, 'groupname', 1),\n" +
                "(213, 'Ophiostoma ulmi', '', 0, 'SE - Svært høy risiko', 53236, 0, 'groupname', 1),\n" +
                "(214, 'Opilio canestrinii', 'gulrotvevkjerring', 0, 'SE - Svært høy risiko', 79955, 0, 'groupname', 1),\n" +
                "(215, 'Pacifastacus leniusculus', 'signalkreps', 0, 'SE - Svært høy risiko', 79815, 0, 'groupname', 1),\n" +
                "(216, 'Paralithodes camtschatica', 'kongekrabbe', 0, 'SE - Svært høy risiko', 14365, 0, 'groupname', 8),\n" +
                "(217, 'Pastinaca sativa hortensis', 'hagepastinakk', 0, 'SE - Svært høy risiko', 60308, 0, 'groupname', 4),\n" +
                "(218, 'Phedimus hybridus', 'sibirbergknapp', 0, 'SE - Svært høy risiko', 63548, 0, 'groupname', 1),\n" +
                "(219, 'Phedimus spurius', 'gravbergknapp', 0, 'SE - Svært høy risiko', 63550, 0, 'groupname', 1),\n" +
                "(220, 'Phytophthora plurivora', '', 0, 'SE - Svært høy risiko', 80540, 0, 'groupname', 1),\n" +
                "(221, 'Phytophthora ramorum', 'greindreper', 0, 'SE - Svært høy risiko', 80542, 0, 'groupname', 1),\n" +
                "(222, 'Picea sitchensis', 'sitkagran', 0, 'SE - Svært høy risiko', 63774, 0, 'groupname', 1),\n" +
                "(223, 'Pinus mugo mugo', 'buskfuru', 0, 'SE - Svært høy risiko', 63779, 0, 'groupname', 1),\n" +
                "(224, 'Pinus strobus', 'weymouthfuru', 0, 'SE - Svært høy risiko', 63784, 0, 'groupname', 1),\n" +
                "(225, 'Populus balsamifera', 'balsampoppel', 0, 'SE - Svært høy risiko', 62594, 0, 'groupname', 1),\n" +
                "(226, 'Populus ◊berolinensis', 'berlinerpoppel', 0, 'SE - Svært høy risiko', 62595, 0, 'groupname', 1),\n" +
                "(227, 'Reynoutria japonica', 'parkslirekne', 0, 'SE - Svært høy risiko', 86678, 0, 'groupname', 1),\n" +
                "(228, 'Reynoutria sachalinensis', 'kjempeslirekne', 0, 'SE - Svært høy risiko', 86679, 0, 'groupname', 1),\n" +
                "(229, 'Reynoutria ◊bohemica', 'hybridslirekne', 0, 'SE - Svært høy risiko', 86677, 0, 'groupname', 1),\n" +
                "(230, 'Ribes rubrum', 'hagerips', 0, 'SE - Svært høy risiko', 63574, 0, 'groupname', 5),\n" +
                "(231, 'Rosa rugosa', 'rynkerose', 0, 'SE - Svært høy risiko', 63363, 0, 'groupname', 1),\n" +
                "(232, 'Salix euxina', 'skjørpil', 0, 'SE - Svært høy risiko', 86666, 0, 'groupname', 1),\n" +
                "(233, 'Salix fragilis', 'grønnpil', 0, 'SE - Svært høy risiko', 86665, 0, 'groupname', 1),\n" +
                "(234, 'Sargassum muticum', 'japansk drivtang', 0, 'SE - Svært høy risiko', 66868, 0, 'groupname', 1),\n" +
                "(235, 'Solidago canadensis', 'kanadagullris', 0, 'SE - Svært høy risiko', 60776, 0, 'groupname', 1),\n" +
                "(236, 'Sorbus intermedia', 'svensk asal', 0, 'SE - Svært høy risiko', 63447, 0, 'groupname', 1),\n" +
                "(237, 'Sorbus mougeotii', 'alpeasal', 0, 'SE - Svært høy risiko', 63453, 0, 'groupname', 1),\n" +
                "(238, 'Suillus grevillei', 'lerkesopp', 0, 'SE - Svært høy risiko', 38890, 0, 'groupname', 9),\n" +
                "(239, 'Sus scrofa', 'villsvin', 0, 'SE - Svært høy risiko', 31237, 0, 'groupname', 16),\n" +
                "(240, 'Swida sericea', 'alaskakornell', 0, 'SE - Svært høy risiko', 61632, 0, 'groupname', 1),\n" +
                "(241, 'Thymus praecox praecox', 'hagekryptimian', 0, 'SE - Svært høy risiko', 62346, 0, 'groupname', 14),\n" +
                "(242, 'Tinca tinca', 'suter', 0, 'SE - Svært høy risiko', 26120, 0, 'groupname', 1),\n" +
                "(243, 'Tricholoma psammopus', 'lerkemusserong', 0, 'SE - Svært høy risiko', 38366, 0, 'groupname', 1),\n" +
                "(244, 'Tsuga heterophylla', 'vestamerikansk hemlokk', 0, 'SE - Svært høy risiko', 63791, 0, 'groupname', 1),\n" +
                "(245, 'Vinca minor', 'gravmyrt', 0, 'SE - Svært høy risiko', 62127, 0, 'groupname', 1),\n" +
                "(246, 'Vincetoxicum rossicum', 'russesvalerot', 0, 'SE - Svært høy risiko', 62130, 0, 'groupname', 1),\n" +
                "(247, 'Viola odorata', 'marsfiol', 0, 'SE - Svært høy risiko', 62761, 0, 'groupname', 1);");
    }

    /**
     * When the app is uninstalled
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        onCreate(db);
    }




    public Specie getSpecie(int id){
        Specie tax = new Specie();

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM blacklist WHERE taxonID = ?", new String[] {String.valueOf(id) });

        if(!c.moveToFirst()) {
            c.close();
            return null;
        }

        tax.setName(c.getString(c.getColumnIndex("navn")));
        tax.setId(id);
        String img = c.getString(c.getColumnIndex("image"));
        Boolean eatable = false;
        if(img != "1"){
            eatable = true;
        }
        tax.setEatable(eatable);
        tax.setLatin(c.getString(c.getColumnIndex("scientificName")));
        tax.setRisk(c.getString(c.getColumnIndex("risiko")));
        tax.setFamily(c.getString(c.getColumnIndex("family")));

        return tax;
    }

    /**
     * Insert a row into the database
     * @param type
     * @param value
     * @return
     */
    private boolean insertData(String type, String value) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(tableColValue, value);
        cv.put(tableColType, type);

        long result = db.insert(tableName, null, cv);

        return result > -1;
    }

    /**
     * Update a row in the database
     * @param type
     * @param value
     * @return
     */
    private boolean updateData(String type, String value){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(tableColValue, value);

        int result = db.update(tableName, cv, tableColType + " = ?", new String[] {String.valueOf(type) });

        return result > -1;
    }

    /**
     * Fetch a value from the database
     * @param type
     * @return value
     */
    public String fetchType(String type){

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + tableName + " WHERE type = ?", new String[] {String.valueOf(type) });


        if(!c.moveToFirst()) {
            c.close();
            return null;
        }


        String val = c.getString(c.getColumnIndex(tableColValue));
        c.close();
        return val;

    }

    /**
     * Update or Insert a value into the database
     * @param type
     * @param value
     */
    public void updateOrInsert(String type, String value){
        if(!insertData(type, value)) {
            updateData(type, value);
        }
    }
}
