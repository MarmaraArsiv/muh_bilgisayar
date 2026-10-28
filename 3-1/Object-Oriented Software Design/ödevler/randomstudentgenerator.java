import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * The RandomStudentGenerator class that creates random students with all randomized information and contains related methods
 */
public class RandomStudentGenerator {
    /**
     * Student first names String array
     */
    private final String[] firstNames = {"JALE", "ALİ", "MAHMUT", "MANSUR KÜRŞAD", "GAMZE", "MİRAÇ", "YÜCEL", "KUBİLAY", "HAYATİ", "BEDRİYE MÜGE", "BİRSEN", "SERDAL", "BÜNYAMİN", "ÖZGÜR", "FERDİ", "REYHAN", "İLHAN", "GÜLŞAH", "NALAN", "SEMİH", "ERGÜN", "FATİH", "ŞENAY", "SERKAN", "EMRE", "BAHATTİN", "IRAZCA", "HATİCE", "BARIŞ", "REZAN", "FATİH", "FUAT", "GÖKHAN", "ORHAN", "MEHMET", "EVREN", "OKTAY", "HARUN", "YAVUZ", "PINAR", "MEHMET", "UMUT", "MESUDE", "HÜSEYİN CAHİT", "HAŞİM ONUR", "EYYUP SABRİ", "MUSTAFA", "MUSTAFA", "UFUK", "AHMET ALİ", "MEDİHA", "HASAN", "KAMİL", "NEBİ", "ÖZCAN", "NAGİHAN", "CEREN", "SERKAN", "HASAN", "YUSUF KENAN", "ÇETİN", "TARKAN", "MERAL LEMAN", "ERGÜN", "KENAN AHMET", "URAL", "YAHYA", "BENGÜ", "FATİH NAZMİ", "DİLEK", "MEHMET", "TUFAN AKIN", "MEHMET", "TURGAY YILMAZ", "GÜLDEHEN", "GÖKMEN", "BÜLENT", "EROL", "BAHRİ", "ÖZEN ÖZLEM", "SELMA", "TUĞSEM", "TESLİME NAZLI", "GÜLÇİN", "İSMAİL", "MURAT", "EBRU", "TÜMAY", "AHMET", "EBRU", "HÜSEYİN YAVUZ", "BAŞAK", "AYŞEGÜL", "EVRİM", "YASER", "ÜLKÜ", "ÖZHAN", "UFUK", "AKSEL", "FULYA", "BURCU", "TAYLAN", "YILMAZ", "ZEYNEP", "BAYRAM", "GÜLAY", "RABİA", "SEVDA", "SERHAT", "ENGİN", "ASLI", "TUBA", "BARIŞ", "SEVGİ", "KALENDER", "HALİL", "BİLGE", "FERDA", "EZGİ", "AYSUN", "SEDA", "ÖZLEM", "ÖZDEN", "KORAY", "SENEM", "ZEYNEP", "EMEL", "BATURAY KANSU", "NURAY", "AYDOĞAN", "ÖZLEM", "DENİZ", "İLKNUR", "TEVFİK ÖZGÜN", "HASAN SERKAN", "KÜRŞAT", "SEYFİ", "ŞEYMA", "ÖZLEM", "ERSAGUN", "DİLBER", "MESUT", "ELİF", "MUHAMMET FATİH", "ÖZGÜR SİNAN", "MEHMET ÖZGÜR", "MAHPERİ", "ONUR", "İBRAHİM", "FATİH", "SEVİL", "SÜHEYLA", "VOLKAN", "İLKAY", "İLKNUR", "ZÜMRÜT ELA", "HALE", "YENER", "SEDEF", "FADIL", "SERPİL", "ZÜLFİYE", "SULTAN", "MUAMMER HAYRİ", "DERVİŞ", "YAŞAR GÖKHAN", "TUBA HANIM", "MEHRİ", "MUSTAFA FERHAT", "SERDAR", "MUSTAFA ERSAGUN", "ONAT", "ŞÜKRÜ", "OLCAY BAŞAK", "SERDAR", "YILDIZ", "AYDIN", "ALİ HALUK", "NİHAT BERKAY", "İSMAİL", "AYKAN", "SELÇUK", "MEHMET", "NEZİH", "MUSTAFA", "TİMUR", "ERHAN", "MUSTAFA", "MUTLU", "MEHMET HÜSEYİN", "İSMAİL EVREN", "OSMAN ERSEGUN", "MEHMET", "ELİF", "SERKAN", "MESUT", "MEHMET HİLMİ", "ASUDAN TUĞÇE", "AHMET GÖKHAN", "BAŞAK", "CEYHAN", "MUHAMMET TAYYİP", "ESİN", "ZEYNEP GÖKÇE", "EVRİM", "YASİN", "SALİHA", "DENİZ", "BELGİN", "ÖZLEM", "GONCA", "ESRA", "SEÇKİN", "ESRA", "FATİH", "MUSTAFA", "FEVZİYE", "MUSTAFA ARİF", "BİRGÜL", "ÖZLEM", "ÖZLEM", "FUNDA", "BERFİN", "DEMET", "SONAY", "SERÇİN", "ALMALA PINAR", "ÜMİT", "SENEM", "DENİZ", "MÜNEVER", "HATİCE", "ÖZLEM", "ÖZLEM", "ALİ SEÇKİN", "COŞKUN", "ÖZGE", "ZELİHA", "PINAR", "AYBÜKE", "HASİBE", "GÜRKAN", "ZÜHAL", "NAZIM", "ZEYNEP", "OSMAN", "AYLA", "BEYZA", "ELİF", "ERAY", "DİANA", "TUBA", "SEMRA", "VELAT", "BELGİN EMİNE", "SİBEL", "GÖKMEN ALPASLAN", "BENHUR ŞİRVAN", "DİLEK", "HANDE", "ŞAHABETTİN", "MİRAY", "ZERRİN", "İLKNUR", "ELİF", "MÜMTAZ", "TUĞBA", "DİLEK", "MEHMET BURHAN", "FUAT", "NİHAL", "AYŞEGÜL", "SEMA", "ZAFER", "NURSEL", "GÜLPERİ", "BİLGE", "FATİH", "CENGİZ", "SİMGE", "SEMA NİLAY", "EMİNE", "RİFAT CAN", "SİNAN", "LATİFE", "MEHMET", "NURDAN", "MELTEM", "ÜLKÜHAN", "HASAN", "GÜLDEN", "SAMET", "BERNA", "ÖZLEM", "NAFİYE", "KENAN", "SERKAN FAZLI", "NURSEL", "ABDULLAH", "ERGÜL", "HASAN", "MUSTAFA", "SEBAHAT", "EMİNE", "ERDAL", "LEZİZ", "BİRSEN", "TUBA", "AYŞEN", "EBRU", "TAYFUR", "MELTEM", "SERHAT", "AYCAN ÖZDEN", "ELİF", "SEVGÜL", "SELDA", "IŞIL", "SİBEL", "JÜLİDE ZEHRA", "BERİL GÜLÜŞ", "İNCİ", "ENGİN", "GÜLBAHAR", "MÜBECCEL", "NURDAN", "HANDE", "ÖZNUR", "HANDAN", "OSMAN TURGUT", "EMİN TONYUKUK", "NEJDET", "MUSTAFA", "GÜLİZ", "İPEK", "NİHAL", "MELDA", "DERYA", "DEMET", "MAHMUT", "EMEL", "ÖZNUR", "SONGÜL", "RESA", "GAMZE", "ÜMİT", "DENİZ", "MUAMMER MÜSLİM", "ÖMER FARUK", "TUĞÇE", "VELİ ENES", "ZAHİDE", "NURETTİN İREM", "SEDAT", "REMZİYE", "SİBEL", "İLKNUR", "YASEMİN", "AYLİN", "EMEL", "EMEL CENNET", "ŞAFAK", "METİN", "SÜLEYMAN", "MUKADDES", "BARIŞ", "MEHMET ALİ", "TEVFİK", "SERDAR", "EMİNE", "MÜRŞİT", "MUTLU", "FEZA", "İBRAHİM TAYFUN", "SERKAN", "AHMET SERKAN", "FATMA", "BERKER", "SERDAR", "KUBİLAY", "ERKAN", "KERİM", "İLKNUR", "SERKAN", "MUSTAFA", "RUKİYE", "GÖKTEN", "SEZGİ", "TUĞBA", "MURAT", "HATİCE", "HATİCE EYLÜL", "AYŞE GÜL", "NEVİN", "HABİBE", "KEZBAN", "AYSEL", "TALHA", "DUYGU", "GÖZDE", "FIRAT", "EBRU", "GÜLEN ECE", "SİBEL", "FULYA", "VEDAT", "HARUN", "FİLİZ", "NURAY", "ŞİRİN", "ÖZLEM", "BURCU", "PINAR", "HATUN", "CEYDA", "BURCU", "AYŞE", "ALPER", "FEYZA", "HACI MURAT", "MÜCELLA", "FEYZAHAN", "ŞENAY", "MERİH", "YUSUF", "ARDA", "EVRE", "KONURALP", "KIVANÇ", "EMİNE", "VOLKAN", "NİHAT", "RENGİN ASLIHAN", "EMRE", "ARİFE ESRA", "SEDAT", "MURAT", "CEM", "ERHAN", "ÖMÜR", "UMUT CAN", "MUSTAFA NAFİZ", "DAMLA", "MÜSLİM", "ABDULKADİR", "SAADET", "REZZAN", "SEDAT", "İBRAHİM", "LEYLA", "TÜLAY", "ENDER", "YELİZ", "ÖZGÜL", "HALE", "BERÇEM", "MUSTAFA", "TUBA", "SABAHATTİN", "ŞAFAK", "EVRİM", "REŞAT", "MUMUN", "FUNDA ÖZLEM", "NUR", "METE", "TÜLAY", "ÖZLEM", "HATİCE NİLDEN", "MELTEM", "EDA", "MELTEM", "MUSTAFA", "YURDUN", "SEMA", "TUBA", "SERPİL", "CENK", "TANER", "ZEKERİYA", "MUHAMMED ALİ", "TUĞRUL", "YÜCEL", "ESMA ÖZLEM", "AHMET", "SEVDE NUR", "SAMİ", "GAMZE", "GÜLSÜM", "SERHAT", "BARIŞ", "GÜLSEREN", "SULTAN", "İLKER", "DERAM", "AHMET", "BALA BAŞAK", "FEVZİ FIRAT", "GÖZDE", "FERHAN", "İLKER", "SALİM", "EMİNE", "MURAT", "ATAKAN", "REFİK", "MUSTAFA", "ÖZGÜR", "İKLİL", "ZÜHAL GÜLSÜM", "MEHTAP", "DENİZ", "ÜMİT", "MEHMET", "VOLKAN", "İLKNUR", "SELÇUK", "ÖZLEM", "GÖKHAN", "METE", "HÜMEYRA", "PAPATYA", "LEVENT", "FADİME SEVGİ", "ERSEN", "ŞULE MİNE", "MELİA", "ŞERMİN", "AYLİA", "AHMET EMRE", "VEDAT", "HALUK", "SEZGİN", "ZEHRA BETÜL", "VOLKAN", "ÜNSAL", "KORAY", "GÜLŞAH", "HİCRAN", "YUSUF KENAN", "YUSUF", "ORHAN", "FÜSUN", "ÖZLEM", "MEHMET", "SERKAN", "İKRAM", "ÜLKÜHAN", "NUH", "İSMAİL", "GÜLŞAH", "AYKUT", "NEŞE", "NEZAKET", "MUHAMMET DEVRAN", "HANDAN", "ATİLLA", "AYŞEGÜL", "PINAR", "ARZU", "NEŞE", "CEYHAN", "HASAN SAMİ", "MEHMET", "SALİH", "FERDA", "DİLEK", "AYHAN", "HASAN ULAŞ", "SUNA", "SELAMİ", "SÜREYYA", "BURCU", "CEM YAŞAR", "ÇAĞDAŞ", "DOĞAN", "AHMET", "HATİCE", "HAYRİ", "GÜHER", "SUNA", "ARZU", "AHMET", "AHMET", "SEMİH", "YUSUF", "ALİ", "ELİF ÇİLER", "ELİF", "YETKİN", "BURAK", "VEYSEL", "BURCU", "REFAETTİN", "AYŞE GÜL", "HÜSEYİN KUNTER", "EMİNE", "EBRU", "ESRA NUR", "SAMİ", "MUSTAFA GÜRHAN", "ORHAN", "HAÇÇE", "MEHMET MURAT", "NAZAN", "TUĞBA", "OĞUZHAN", "BERFİN CAN", "ÖZGÜR", "CİHAN", "DERMAN", "NİHAL", "IŞIN", "HATİCE", "DİDEM", "SUAT", "SİMENDER", "ADNAN", "SEZİN", "ŞERAFETTİN", "BERRİN", "ÖZGÜR", "TAYFUR", "SERHAT", "FUNDA", "NESLİHAN", "SERVET", "EMRE", "ORÇUN", "FATMA ESİN", "DERYA", "DUYGU", "HÜSEYİN", "AYKUT", "AYŞE", "SEHER ÖZLEM", "SEDA", "ESRA CAN", "EVREN", "NİLÜFER", "MURAT", "YUSUF", "MUSTAFA BARAN", "GÜNEŞ", "FATİH", "MEHMET", "ORHAN", "PINAR", "ERHAN", "GÜLDEN", "LATİFE", "SİBEL", "FATMA SELCEN", "HALİS", "YELDA", "ZEHRA", "MEHMET REŞİT", "EMCED", "OKAN", "ABDULLAH ARİF", "ÖZLEM", "AYDEMİR", "FATİH", "AYDIN", "BENGÜHAN", "AHMET", "TOLUNAY", "TUĞRA", "ÖZGÜR", "YUSUF", "ÖNDER TURGUT", "BARAN", "SEYHAN", "ZEKİ", "KADİR", "MURAT", "İHSAN", "DEMİR", "MUHAMMET MURAT", "MUHAMMED", "BAHADIR", "İLHAN", "YILDIRIM", "OĞUZ KAAN", "EFTAL MURAT", "GÖKAY", "FATİH RIFAT", "NURAN", "HABİL", "ÖMER ÖZKAN", "SELMA", "NURETTİN", "AHMET", "UTKU", "SÜLEYMAN", "CAN", "ONUR KADİR", "FİLİZ", "CANSU SELCAN", "ABDULLAH", "NESLİHAN", "SEDA ELÇİM", "KÜBRA", "HÜSEYİN", "BELMA", "MÜCAHİT", "CEYHUN", "GÜLTEKİN  GÜNHAN", "HANDE", "GÜLHANIM", "ÖMER", "ZİYA", "ÇAĞRI", "SERKAN", "LEVENT", "İSA", "CEM", "HÜSEYİN", "CEMİLE ÇİĞDEM", "MAHMUT", "ÖNDER", "RAŞAN", "İSMAİL YAVUZ", "ÖMER", "KENAN SELÇUK", "EMRE", "SERDAR", "SONER", "ENVER", "MUHLİS", "TİMUR", "LEMAN", "ELA", "CEMİL", "BURCU", "SALİHA SANEM", "ZELİHA", "ALEVTİNA", "ARZU", "MEHMET", "BİREYLÜL", "AYSEL", "ÖZGÜL", "SIDIKA", "TUNA", "ÖVGÜ ANIL", "EMİŞ", "NİLGÜN", "ELİF", "SEBİHA", "YUSUF ALPER", "IŞIL", "AYŞEGÜL", "TÜMAY", "ZERİN", "MEHMET", "FATMA ECE", "AHMET", "NEVROZ", "SEMA", "GAMZE PINAR", "FATMA", "MELTEM HALE", "SELMA", "İBRAHİM", "ASLIHAN", "FİLİZ", "BİLGİ", "TUĞBA", "HÜSEYİN", "EVREN", "FERDİ", "BURÇİN", "BARIŞ", "BAVER", "MAHMUT", "EMRAH KEMAL", "MURAT", "MEHMET ÖZER", "GÖKAY", "NECİP", "ERKAN", "ÜMİT", "GANİM", "BARAN", "FATİH", "SANCAR", "MUSTAFA KEMAL", "DURAN", "HASAN", "GÖRKEM", "ALİ OZAN", "VELİ ÇAĞLAR", "İRFAN", "SEYFİ CEM", "CÜNEYT", "ALİ", "FIRAT", "MUHAMMED TAHA", "BURAK", "MUSTAFA", "NİZAMETTİN", "RECEP GANİ", "ARZU", "MAHMUT NURİ", "GÜNAY", "BESTE", "CEM", "EMRAH", "HALİL İBRAHİM", "ESEN İBRAHİM", "MELİKE", "MÜRSEL", "KORAY", "UMUT SİNAN", "İBRAHİM BARIŞ", "BURHAN", "MUSTAFA KÜRŞAT", "SERDAR BORA", "FUAT", "ELİF", "SİBEL", "ADEM", "CEM İNAN", "GÖKTEKİN", "DİLARA", "SEDA", "CUMHUR", "HALUK", "PINAR", "AYKUT", "ÖYKÜ", "BAŞAK", "ÖMER", "SERHAN", "SULTAN", "MİNE CANSU", "SUAT", "ÇİĞDEM", "HANDE", "UMUT SEDA", "EVRİM", "DİCLE", "İREM", "FATMA", "İLKNUR", "CEMİLE AYŞE", "FEYZA", "MUAMMER", "EBRU", "DİNÇER AYDIN", "AYŞE AHSEN", "PINAR", "SÜREYYA BURCU", "MARİA", "SEÇİL", "BARIŞ", "ŞAHİNDE", "MEHMET", "NEVRİYE", "NİLAY", "RÜŞTÜ", "ASLI", "TANSU", "ASLIHAN", "NURCAN", "DEMET", "ERDEM", "BETÜL EMİNE", "ÇETİN", "PINAR", "RASİM", "AHMET", "ZEHRA", "SELİM", "DENİZ", "MESUT", "AYSEL", "MÜMÜNE", "DUÇEM", "YAKUP", "ERCAN", "MEHMET GÖKÇE", "ATİLLA SÜLEYMAN", "MERAL", "NURDAN", "SEÇİL", "ELİF", "HASAN BİLEN", "NİLAY", "HİLAL", "MUSTAFA", "ŞEYMA MELİHA", "MÜJDAT", "BURCU", "ARİF", "AYŞE", "HACİ HALİL", "IŞIK", "SERAY", "SERHAT BURKAY", "EMİN", "ÇAVLAN", "ŞEREF CAN", "KADİR", "MÜBERRA", "AYŞE", "HASAN", "SAVAŞ", "AYŞE NUR", "SÜLEYMAN", "DUYGU", "HACI MEHMET", "MEHTAP", "ERKAN", "SEMİNE", "ELİF", "RAMAZAN", "AHMET EMRE", "SERKAN", "DENİZ", "MİNE", "FATİH", "İREM", "ENDER", "YASEMİN", "SEMA", "MUSTAFA ULAŞ", "ALİ", "EMİNE", "TÜLAY", "BİRSEN", "GÜLSEN", "TUBA", "HAYRİYE", "BAHADIR", "SEZEN", "EDİP GÜVENÇ", "MEHTAP", "FİLİZ", "EYLEM", "RAMAZAN", "BİLGİN", "ESRA", "ATİYE MELTEM", "METİN", "ÖZLEM", "OSMAN", "AYŞE", "HİLAL", "YAVUZ", "ÖZLEM", "AYŞE", "MUSTAFA", "ASUMAN", "ÖMER", "ŞENAY", "GÜLNAME", "ÖZLEM", "BETÜL", "EMİNE DİLEK", "ZELİHA", "ESİN SEREN", "FİLİZ", "ULAŞ", "HALE", "ADEM", "İLKER", "ERHAN", "FARUK", "İBRAHİM", "SERKAN", "MAHMUT ESAT", "ERAY", "OKTAY", "DENİZ", "OSMAN", "İZZET", "İHSAN", "URAL", "ARİF", "AZİZ", "FUAT ERNİS", "HAKAN", "MUZAFFER OĞUZ", "MAHİR", "TAYYAR ALP", "EREM", "CİHAN", "MEHMET", "ÜMİT", "MEHMET", "YASEMİN", "CEYDA", "FATIMA İLAY", "NİLAY", "HURİYE", "MERVE", "ŞEYMA", "SELAHATTİN"};
    /**
     * Student last names String array
     */
    private final String[] lastNames = {"ŞEN", "KANDEMİR", "ÇEVİK", "ERKURAN", "TÜTEN", "ÖZTÜRK", "YÜZBAŞIOĞLU", "VURAL", "YÜCEL", "SÖNMEZ", "ERTEKİN", "DEDE", "UYANIK", "ASLAN", "AKBULUT", "ORHON", "UZ", "YAVUZ", "ERDEM", "KULAÇ", "KAYA", "SELVİ", "AKPINAR", "ABACIOĞLU", "ÇAY", "IŞIK", "ÖZER", "ÖZDEMİR", "ÖZTÜRK", "TAHTACI", "BÜYÜKCAM", "KULAKSIZ", "AKSEL", "EROĞLU", "KARAKUM", "DAL", "ÖCAL", "AYHAN", "YİĞİT", "YARBİL", "GÜMÜŞAY", "MURT", "HALHALLI", "ULUÖZ", "ŞEYHANLI", "YILMAZ", "SARAÇOĞLU", "SEZER", "DOĞAN", "DEMİR", "KAYAYURT", "SÜRÜM", "YAVAŞİ", "TURGUT", "BARBAROS", "ALDİNÇ", "TEKİN", "GÜLŞAN", "KÜFECİLER", "ALMACIOĞLU", "ÇİLDİR", "TÜRKDOĞAN", "KAYA", "ÖNER", "ŞELİMAN", "YAMAN", "ATİK", "YİĞİT", "GİRAY", "YALÇINKAYA", "KILIÇ", "ŞENTÜRK", "KARABAĞ", "DEĞİRMENCİ", "BODUROĞLU", "YILDIZ", "GÜLER", "ERASLAN", "ÜZER", "PİŞİRGEN", "ADANIR", "KOÇ", "KORKMAZ", "YENİDOĞAN", "AYDOĞAN", "ACARBULUT", "ERGE", "ERDOĞAN", "ÖĞÜT AYDIN", "KUŞKU", "KUCUR TÜLÜBAŞ", "PEKTAŞ", "KAYACAN", "GÜLEN", "DOĞAN", "CANDAN", "TEMEL", "YENİGÜN", "YILDIRIM", "BEDER", "AKINCI", "ÖZDEMİR", "ONUK", "AYDOĞAN", "YILMAZ", "CÖMERT", "TOPAL", "KARAHAN", "ŞAHİN", "ÇETİN", "AYTAÇ", "KİŞİ", "GÜNDÜZ", "AK", "URFALI", "KARAMAN", "MEMETOĞLU", "KAZBEK", "KİREÇÇİ", "AKIN", "YADİGAROĞLU", "YÜKSEL", "ÖZÇELİK ORAL", "BABUŞ", "KAPLAN", "AKÖZ", "KARTAL", "BİLGİÇ", "ERDEN", "TUĞCUGİL", "KUMRAL", "ERBAŞ", "ORAL", "KILAÇ", "CENGİZ", "YILDIRIM", "BALABAN", "KAYA", "BALCI", "TÜFEKÇİ", "ATAY", "YARAR", "SEVER", "YILDIRIM", "ARKAN", "TUTAŞ", "ÖZTÜRK", "HAVAS", "SEÇİR", "YILDIZ", "SOYKAMER", "BEKTAŞ", "BERK", "GÜL", "CENGİZ", "ÇOLAK", "BULUT", "SARI", "AKYOL", "BAĞCIK", "KUTLUYURDU", "DEMİRGAN", "GERİLMEZ", "DÜZKALIR", "KÖKSOY", "GÜLŞEN", "AKAR", "ÖZDOĞAN", "TÖNGE", "YASA", "ÖNVERMEZ", "YILDIRIM", "BİÇER", "KARADEMİR", "ALIMLI", "AKGÜL", "HANCIOĞLU", "BATÇIK", "OLPAK", "BOLAT", "ARSLAN", "SİĞA", "MERCAN", "BOZKURTER", "GÜLER", "ERGİNEL", "ŞAHİN", "KADAK", "HEPKAYA", "BAYRAM", "ESER", "GİDER", "KURT", "ELLİALTI", "DEMİRTAŞ", "ARGA", "ALUÇLU", "MUTLU", "ENGİZ", "ÇİPE", "UYSAL", "BAŞER", "ARSLAN", "GÖZKAYA", "ULUTAŞ", "PİRİM", "ÜSTÜN", "KIZMAZOĞLU", "ULUBA", "ARSLAN", "KARAOĞLU", "ÖZSOY", "YALÇIN", "SAF", "VURAL", "DEMİRTAŞ", "GENÇPINAR", "AKASLAN", "UYĞUN", "ATAY", "BAYMAK", "ATAY", "GÜVENÇ", "ÖZCAN", "BAŞMAN", "YANNİ", "ÜNAL", "GÜNDOĞDU", "ÇELİK", "TAŞKIN", "ÇETİN", "SARİ", "KARAKOYUN", "EKİCİ", "AYDINER", "AKTAŞ", "BELGEMEN", "ÇETİN", "OFLAZ", "BUĞRUL", "BAYSOY", "BÜKÜLMEZ", "YILMAZ", "BIÇAKÇI", "KARA", "ATEŞ", "BİNBOĞA", "KIZILTEPE", "KAYA", "ABSEYİ", "ÖZTÜRK", "TAŞ", "CEYLAN", "KILIÇ", "EROL", "TAYFUN", "KAYA", "KARAKURT", "BUDUNOĞLU", "ÖZER", "SAYGIN", "ERYAVUZ", "YILMAZ", "ÇELİK", "ÜNSAL", "ALPINAR", "CİNDEMİR", "AKDUMAN", "UYAR", "TÜLPAR", "AZAK", "EREN", "GÖZCÜ", "BAYSAL", "TUNCEL", "ÇETEMEN", "YILMAZ", "GİNİŞ", "UZUN", "NASIROĞLU", "SEZGİN", "ÖZTÜRK", "YILDIRIM", "UZUN", "BULUR", "DUYSAK", "YENİN", "DEMİREL", "SAK", "KOCABAŞ", "SARAÇ", "YURT", "İLKAY", "TAVŞAN", "ALAY", "ERTEM", "ÖZEL", "GENÇ", "UĞUZ", "EVİK", "EKER", "ÇİMEN", "ÇIRAKOĞLU", "ALPAYCI", "AK", "ÇELİK", "ERCAN", "ALTUN", "KILIÇ", "SARP", "SÖKER", "KÖSE", "BARÇAK", "BOLAÇ", "ASLANALP", "ÖRNEK", "AKDOĞAN", "ÖZÇELİK", "ERTÜRKLER", "SARAL", "ÖZKAN", "DEMİRHAN", "ASLANKARA", "EMLAKÇIOĞLU", "ÖZTÜRK", "ESER", "ÖDEN", "DEMİRAY", "AYHAN", "YAĞCI", "AVCI", "BAYGELDİ", "BÜKÜM", "DİNCER", "DOĞAN", "EKİZ", "ŞAHİNER", "ŞENGÜL", "İLGÜN", "AŞIK", "ÖZKAN", "ŞİRZAİ", "ÖCALAN", "KABA", "TÜLÜCE", "AYTEKİN", "KAYA", "DÜGER", "METİNEREN", "BULUT", "ÇETİN", "BÖLÜK", "GÖZAÇAN", "BOZKURT", "AY GÜNEY", "KÖYLÜ", "TALAN", "DUMLU", "KÖYCÜ", "UYGUR", "KABACAOĞLU", "TOPALOĞLU", "AYIK", "DEMİR", "ERDEM", "KARAMANLI", "OKTAY", "YURTLU", "NURÇİN", "BAŞKAN", "KAZANCI", "METE", "UZUN", "SAĞDIK", "EKİCİ", "KESİM", "GÜL", "YILDIRIM", "KARAGÖZ", "PEKEL", "YAKAR", "TARLAN", "ÇATAK", "ÇETİNKOR", "SAYIN", "KURT", "GÜMÜŞ", "ÖZMEN", "GÜNAY", "DÜZ", "DİLEK", "DEMİRTAŞ", "KURTULUŞ", "KARPUZOĞLU", "BEYOĞLU", "SULHAN", "ARSLAN", "NUHVEREN", "AVCIOĞLU", "AHISKALI", "ASENA", "GÖLEMEZ", "YILDIRIM", "TOHUMOĞLU", "ÇELİK", "BOZARSLAN", "KÖŞKER", "ÇELİK", "SÜL", "KORKMAZ", "TARKAN", "DUMAN", "HODJAOGLU", "BALLI", "ŞATIROĞLU", "ÖNDE", "LAĞARLI", "ÖZÇAY", "ARSLAN", "AKDEMİR", "İÇBAY", "AKIN", "DEMİRÖZ", "KUYUCU", "SELÇUK", "İNCE", "GÖKALP", "BABACAN", "ÜLGER", "AYVAZ", "ELVERDİ", "AYDIN", "DEMİREL", "CİMBEK", "FIRAT", "BAHÇEBAŞI", "DAM", "KÖROĞLU", "ÖZÇELİK", "KARACA", "SEVEN", "ÖZKURT", "ALTUN", "BÜYÜKTAŞ", "SERTKAYA", "YALNIZ", "SAVAŞ", "YILMAZ", "YALÇIN", "BEREKET", "KAYA", "PEKGÖZ", "DEMİR", "OLMAZ", "SEVİNÇ", "MERHAMETSİZ", "ÇOBANOĞLU", "ŞİMŞEK", "BİNNETOĞLU", "ÇINKIR", "CAMCI", "YAZAK", "NİZAM", "TÜRKOĞLU", "DEMİRKOL", "AKSAKAL", "AKIN", "BOZOĞLAN", "DEĞİRMENCİ", "AYMAN", "SAÇLI", "KARAKILIÇ", "KARAKÖSE", "GÜVEN MEŞE", "YEŞİLOVA", "EŞKAZAN", "GERDAN", "MUMCUOĞLU", "VATANSEVER", "PAKÖZ", "ATMIŞ", "AKÇALI", "FAKIOĞLU", "YENİDÜNYA", "ANIK", "KÖSEOĞLU", "SONAY", "ÇELİKER", "ÖZDEMİRKRAN", "ÇELİK", "KÖSE", "AKIN", "BEREKATOĞLU", "DİLLİ", "ELBÜKEN", "BAHÇECİ", "BÜLBÜL", "KADI", "IŞIK", "YÜCETÜRK", "BULUR", "ÖZİŞ", "ULUBAŞOĞLU", "AKŞAHİN", "KARPUZ", "YABUL", "GÖKSOY", "ÜNAL", "IŞIK", "KÖKSAL", "TEKİŞ", "AKSOY", "BAŞYURT", "YURDSEVEN", "ERDEM", "MERDEN", "SANHAL", "ŞAHİN", "VATANSEVER", "BİLGİ", "KAYABAŞ", "GÜRBOSTAN", "BOLAT", "TAYYAR", "ŞAHBAZ", "YANCAR", "OLGAÇ", "EKİZ", "EREN", "MALÇOK", "KARASU", "KARADAĞ", "TOPRAK", "SAĞLAM", "ŞAHİN", "KEBAPCILAR", "TATAR", "ARSLAN", "YÜCE", "TOLA", "GÜNGÖR", "KARAGÖZ", "ALTINBOĞA", "YENİÇERİ", "IŞIKALAN", "ÖZDEMİR", "GÜRBÜZ", "KURU", "YURDAM", "KARA", "ÇETİN", "BAŞARAN", "ŞAHİN", "ÜREYEN", "IŞIK", "ÖZTÜRK", "DOĞAN", "ORHAN", "VURAL", "EROL", "BALSAK", "ÖZDEMİR", "ÇİFT", "ŞEN", "BAYRAMOĞLU", "GENÇDAL", "DESTEGÜL", "ÖZDEMİR", "KARÇİN", "ASLAN", "BAZ", "ALTUNTAŞ", "ÖZCAN", "KIRBAŞ", "YILMAZ", "ÇETİN", "YEŞİLDAĞER", "YÜKSEL", "KAYAOĞLU", "KILIÇ", "CELTEMEN", "GÜNDÜZ", "ŞANLIKAN", "ÇELİK", "ORHAN", "TANTEKİN", "KARAALP", "TUNCER", "ATASOY", "DOĞAN", "GÖK", "ÖZDEMİR", "ÖZKAN", "ASOĞLU", "KHALİL", "YURDAKÖK", "YILMAZ", "KESKİN", "KOÇARSLAN", "GÖKALP", "TUNÇAY", "AYDIN", "SEVİNGİL", "GENÇPINAR", "AKKAYA", "KUSERLİ", "BOZKURT", "ŞİMŞEK", "YILMAZ", "TALAS", "ÇEVİKER", "KAYNAK", "BAYRAKTAR", "ÇETİNTAŞ", "CANTÜRK", "KARADENİZ", "ALAN", "KOYUNCU", "KARTAL", "KAYA", "BAKIRCI", "NAR", "ULUSOY", "CELİLOĞLU", "YÜCEL", "DUMAN", "AKDENİZ", "YERAL", "GÜRDAL", "KÜTÜK", "KANYILMAZ", "ÖZBEK", "UYSAL", "AKIN", "AKDENİZ", "KAPLAN", "ALBAYRAK", "YILDIRIM", "MÜEZZİNOĞLU", "AYHAN", "UYGUR", "TÜFENK", "YÜCEL", "DEMİR", "ÖZDEMİR", "KIRIŞ", "KIRASLAN", "SALTÜRK", "AÇIKGÖZ", "YAĞCI", "SEVÜK", "KAYA", "DOĞAN", "YILDIZ", "BAYTAN", "DEMİRTAŞ", "MUTLU", "GENÇ", "AKTUĞ", "SERİN", "TUNCAY", "GÜNBEY", "KAYA", "TAŞAR", "AVSEREN", "BAL", "BATMAZ", "ARAZ SERVER", "GÜLER", "ERSOY", "TAY", "ERYILMAZ", "DEMİR", "AYDIN", "OCAK", "BÖLÜK", "ÖZMEN", "ÖZTÜRKER", "EKEN", "AKGÜL", "CANSEVER", "AKIN", "GÜZEL", "ÇAKIR", "AKSUN", "BALAL", "BAYAM", "ŞAİR", "ÜNLÜ", "YUMURTAŞ", "AKGÜL", "AYKAN", "CANATAN", "MUMCUOĞLU", "TAŞKIRAN", "HATİPOĞLU", "AKYOL", "SUCAK", "YILDIZ", "AKPINAR", "GÖKSEL", "KARSLI", "ÖZGÜROL", "ACAR", "KALEM", "ŞAHİN", "AYDIN", "DÖKMECİ", "GÖRMELİ", "ÖZATEŞ", "SERVET", "TOPRAK", "SÜNER", "SARIKAYA", "SULUOVA", "SERBEST", "EFE", "TOPAK", "ATBİNİCİ", "KIYAK", "ÇELİK", "ÖZ", "TEPE", "ÖZÜAK", "ÖNCEL", "CANBAZ", "AL", "DEMİR", "GÜRER", "GÜNGÖR", "GÜZEL", "GÖNCÜ", "ÖZDAMAR", "KARATOPRAK", "ÇAVDAR", "KARA", "ÖZ", "SÖZEN", "GÖKÇEK", "KARAKAYA", "GÜNGÖR", "ÇEPNİ", "KIR", "ERSOY", "ÇAĞLAR", "ÖZALP", "EVRENOS", "BAYRAKTAROĞLU", "USLUSOY", "SARI", "ATALAY", "TOPKARA", "BEKTAŞ", "TENEKECİ", "ÇAĞIL", "MERTOL", "TAŞ", "HIDIROĞLU", "KARAHAN", "DEMİREL", "YACI", "IŞIKLI", "KILIÇ", "ÜLGEN", "SU KURT", "KOÇAR", "BALOĞLU", "DUMAN", "ASLAN", "SERT", "ALTUN", "GÖRMELİ", "AKYOL", "AKYILMAZ", "BAKAN", "KARAKAN", "GÖRKEM", "KARACAN", "TEN", "ATLANOĞLU", "ÖZTÜRK", "TOPALOĞLU", "SOYDAN", "TÜRKAY", "MENTEŞ", "PINARBAŞILI", "ONAY", "CERİT", "ÜNAL", "ALTUN", "YILDIZ", "İMAMOĞLU", "YANMAZ", "BÜBER", "AKKAYA", "BAKAN", "TAŞMALI", "BULAKÇI", "BAYRAM", "AYDIN", "GERGER", "YEŞİLKAYA", "DÖNMEZ", "YILMABAŞAR", "DİKİCİ", "ARİFOĞLU", "FİDAN", "SAKARYA", "ÖZEN", "ONAN", "AKHUN", "KIR", "ŞAHİN", "SU DUR", "YAZICI", "GÜRDEMİR", "ALTINSOY", "ŞAŞMAZ", "GÜLCAN", "ÖZTÜRK", "ULUTAŞ", "ALTUNA", "GÜREL", "KARAKUŞ", "KILIÇ", "ÖZKIRIŞ", "KAYA", "YILMAZ", "DEMİR", "EREN", "BİCAN", "AYDIN", "KÖKSAL", "SARGIN", "AKKOYUNLU", "ŞİMŞEK", "ÖZTÜRK", "KAYHAN", "TEZER", "KARACAN", "ÇAKIR", "UYSAL", "GÜRAKAN", "DOKUMACIOĞLU", "KIRHAN", "ÖZDEMİR", "KAYA", "ESEN", "EKER", "ÇEKİÇ", "SAVRAN", "GÖKALP", "GÖKMEYDAN", "EMRE", "KÜTÜKCÜ", "DİKOĞLU", "AKSOY", "GÖRENEKLİ", "KOCA", "KILINÇ", "KİRİŞCİ", "DEDE", "KIZMAZ", "ARGON", "ALICI", "ARIKAN", "ÜÇER", "ÜNAL", "YILMAZ", "ÇETİN", "ERGÖZ", "FİLİZ", "ALABALIK", "KIZANOĞLU", "YAŞAR", "ÇELEN", "DEMİRELLİ", "DOĞAN", "DÖNMEZ", "AKDEMİR", "DANIŞOĞLU", "GÜRSOY", "ŞENER", "ABAT", "ERGÜN", "ÇİÇEKBİLEK", "ÜNÜŞ", "OĞUZ", "KOL", "TOKER", "SU", "POLAT", "KELEŞ", "SEYREK", "ÖZKAN", "ASİL", "TOKTAŞ", "ARDIÇ", "ÖZDEMİR", "SÖZEN", "ÇOBANYILDIZI", "MÜFTÜOĞLU", "AKSOY", "AKYOL", "OFLAZ", "KARAOĞLANOĞLU", "AYAS"};
    /**
     * Randomly generated semester year
     */
    private int semester;
    /**
     * Randomly generated student register year
     */
    private int registerYear;
    /**
     * Randomly generated student id
     */
    private int id;
    /**
     * A reference of the Student class
     */
    private Student student;
    /**
     * A reference of the Lecturer class
     */
    private Lecturer advisor;
    /**
     * A reference of the Department class
     */
    private Department department;

    /**
     * RandomStudentGenerator constructor
     */
    public RandomStudentGenerator() {
    }

    /**
     * Create a student and set student's information
     *
     * @param department Department to select
     * @param count      Student count to generate proper id
     * @param semesterNo To select semester
     * @return A student with his/her information
     */
    public Student createStudent(Department department, int count, int semesterNo) {
        String name = nameGenerator();
        semester = semesterNo;
        setRegisterYear();
        this.department = department;
        generateId(count);
        setRandomAdvisor();
        student = new Student(name, department);
        student.setStudentID(id);
        student.setSemester(semester);
        student.setRegisterYear(registerYear);
        student.setEducationLevel("undergraduate");
        student.setAdvisor(advisor);
        student.takeMandatoryCourses();
        student.takeElectiveCourses();
        student.createStudentCourses();
        department.addStudent(student);
        advisor.addStudent(student);
        return student;
    }

    /**
     * Create equal number of students for each year
     *
     * @param department   Department to create students for
     * @param studentCount Total number of students for each year to create
     * @return List of students
     */
    public ArrayList<Student> createStudents(Department department, int studentCount) {
        ArrayList<Student> allStudents = new ArrayList<>();
        int[] fallSemesters = {1, 3, 5, 7};
        int[] springSemesters = {2, 4, 6, 8};
        int[] currentSemesters;
        if (department.getCurrentSemester().equals("fall")) {
            currentSemesters = fallSemesters;
        } else {
            currentSemesters = springSemesters;
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < studentCount; j++) {
                allStudents.add(createStudent(department, j, currentSemesters[i]));
            }
            // Reset capacity after each year
            department.resetCapacities();
        }
        logStudent(allStudents);
        return allStudents;
    }

    /**
     * Generate a new random grade using base grades array
     *
     * @return A random grade
     */
    public static int randomGrade() {
        int[] baseGrades = {10, 15, 20, 25, 30, 40, 40, 40, 50, 50, 60, 60, 60, 60, 70, 70, 70, 80, 80, 80, 85};
        int i = (int) (Math.random() * baseGrades.length);
        return (int) Math.ceil(baseGrades[i] + (Math.random() * 15));
    }

    /**
     * Choose a random advisor
     */
    private void setRandomAdvisor() {
        advisor = department.getLecturers().get(registerYear % 4);
    }

    /**
     * Generate a register year
     */
    private void setRegisterYear() {
        int currentYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        registerYear = (int) Math.ceil(currentYear - semester / 2.0);
    }

    /**
     * Generate a id
     *
     * @param count To generate id for student
     */
    private void generateId(int count) {
        id = department.getDepartmentNumber() * 100000 + (registerYear % 100) * 1000 + count;
    }

    /**
     * Generate a random first and last name using firstNames and lastNames arrays
     *
     * @return A random name
     */
    private String nameGenerator() {
        String first = firstNames[(int) (Math.random() * firstNames.length)];
        String last = lastNames[(int) (Math.random() * lastNames.length)];
        return first + " " + last;
    }

    /**
     * Write student logs to Stulog.txt file
     *
     * @param students List of students
     */
    private void logStudent(List<Student> students) {
        Logger logger
                = Logger.getLogger(
                RandomStudentGenerator.class.getName());
        FileWriter stuWriter;
        try {
            stuWriter = new FileWriter("StuLog.txt");
            for (Student student : students) {
                StringBuilder studentLog = new StringBuilder("\n" + student.getName() + " (" + student.getStudentID() + ") " + "registered.\n");
                for (Map.Entry<DepartmentCourse, StudentCourse> course : student.getNewlyTakenCourses().entrySet()) {
                    studentLog.append("He/She taken ").append(course.getValue().getMainCourse().getCourseName()).append(" this year \n");
                }
                //logger.log(Level.INFO, String.valueOf(studentLog));
                stuWriter.append(studentLog.toString());
                stuWriter.flush();
            }
            stuWriter.close();
        } catch (IOException ex) {
            System.out.println("Couldn't create the file.");
        }
    }
}
