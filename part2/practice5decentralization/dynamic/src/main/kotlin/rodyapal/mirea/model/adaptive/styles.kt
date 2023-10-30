package rodyapal.mirea.model.adaptive

const val STYLE_DARK_BODY = """
	--text-color: #eee;
	--bkg-color: #121212;
	display: flex;
	justify-content: center;
	align-items: center;
	flex-direction: column;
	background: var(--bkg-color);
"""

const val STYLE_DARK_BUTTON = """
	margin: 10px;
	height: 40px;
	border: 0;
	width: 200px;
	background: white;
	color: black;
	font-family: Arial, Helvetica, sans-serif;
"""

const val STYLE_LIGHT_BODY = """
	--text-color: #222;
	--bkg-color: #fff;
	display: flex;
	justify-content: center;
	align-items: center;
	flex-direction: column;
	background: var(--bkg-color);
"""

const val STYLE_LIGHT_BUTTON = """
	margin: 10px;
	height: 40px;
	border: 0;
	width: 200px;
	background: blue;
	color: white;
	font-family: Arial, Helvetica, sans-serif;
"""

const val STYLE_INPUT = """
	height: 40px;
	width: 200px;
	font-family: Arial, Helvetica, sans-serif;
"""

const val STYLE_TEXT = """
	color: var(--text-color);
	font-family: Arial, Helvetica, sans-serif;
"""