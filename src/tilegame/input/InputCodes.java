package tilegame.input;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * This abstract class is used as a database for all inputs that other classes can then use.
 * @author Kenneth Lange
 *
 */
public abstract class InputCodes {

	public static int
	//keyboard
	KEY_1 = KeyEvent.VK_1,
	KEY_2 = KeyEvent.VK_2,
	KEY_3 = KeyEvent.VK_3,
	KEY_4 = KeyEvent.VK_4,
	KEY_5 = KeyEvent.VK_5,
	KEY_6 = KeyEvent.VK_6,
	KEY_7 = KeyEvent.VK_7,
	KEY_8 = KeyEvent.VK_8,
	KEY_9 = KeyEvent.VK_9,
	KEY_0 = KeyEvent.VK_0,
	KEY_A = KeyEvent.VK_A,
	KEY_B = KeyEvent.VK_B,
	KEY_C = KeyEvent.VK_C,
	KEY_D = KeyEvent.VK_D,
	KEY_E = KeyEvent.VK_E,
	KEY_F = KeyEvent.VK_F,
	KEY_G = KeyEvent.VK_G,
	KEY_H = KeyEvent.VK_H,
	KEY_I = KeyEvent.VK_I,
	KEY_J = KeyEvent.VK_J,
	KEY_K = KeyEvent.VK_K,
	KEY_L = KeyEvent.VK_L,
	KEY_M = KeyEvent.VK_M,
	KEY_N = KeyEvent.VK_N,
	KEY_O = KeyEvent.VK_O,
	KEY_P = KeyEvent.VK_P,
	KEY_Q = KeyEvent.VK_Q,
	KEY_R = KeyEvent.VK_R,
	KEY_S = KeyEvent.VK_S,
	KEY_T = KeyEvent.VK_T,
	KEY_U = KeyEvent.VK_U,
	KEY_V = KeyEvent.VK_V,
	KEY_W = KeyEvent.VK_W,
	KEY_X = KeyEvent.VK_X,
	KEY_Y = KeyEvent.VK_Y,
	KEY_Z = KeyEvent.VK_Z,
	//Other keys
	KEY_SHIFT = KeyEvent.VK_SHIFT,
	KEY_SPACE = KeyEvent.VK_SPACE,
	KEY_CONTROL = KeyEvent.VK_CONTROL,
	KEY_ALT = KeyEvent.VK_ALT,
	KEY_ENTER = KeyEvent.VK_ENTER,
	KEY_ESCAPE = KeyEvent.VK_ESCAPE,
	
	KEY_UP = KeyEvent.VK_UP,
	KEY_DOWN = KeyEvent.VK_DOWN,
	KEY_LEFT = KeyEvent.VK_LEFT,
	KEY_RIGHT = KeyEvent.VK_RIGHT,
	//F?s
	KEY_F1 = KeyEvent.VK_F1,
	KEY_F2 = KeyEvent.VK_F2,
	KEY_F3 = KeyEvent.VK_F3,
	KEY_F4 = KeyEvent.VK_F4,
	KEY_F5 = KeyEvent.VK_F5,
	KEY_F6 = KeyEvent.VK_F6,
	KEY_F7 = KeyEvent.VK_F7,
	KEY_F8 = KeyEvent.VK_F8,
	KEY_F9 = KeyEvent.VK_F9,
	KEY_F10 = KeyEvent.VK_F10,
	KEY_F11 = KeyEvent.VK_F11,
	KEY_F12 = KeyEvent.VK_F12,
	//Mouse
	LEFT_MOUSE = MouseEvent.BUTTON1,
	MIDDLE_MOUSE = MouseEvent.BUTTON2,
	RIGHT_MOUSE = MouseEvent.BUTTON3;
}
