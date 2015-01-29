

import jade.content.lang.sl.SLCodec;
import jade.domain.FIPANames;

public class BlottoGlobals {
	static SLCodec codec;
	public static int CFP_WAIT = 3000;
	public static int SLEEP = 20000;
	public static String LANGUAGE = FIPANames.ContentLanguage.FIPA_SL;

	public static SLCodec getCodec() {
		if (codec == null)
			codec = new SLCodec();
		return codec;
	}
}
