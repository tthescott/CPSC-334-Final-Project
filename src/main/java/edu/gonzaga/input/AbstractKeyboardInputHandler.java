package edu.gonzaga.input;

import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public abstract class AbstractKeyboardInputHandler extends AbstractInputHandler implements InputHandler {
	protected final Component component;
	protected final Key[] keys;
	
	private final KeyAdapter keyAdapter;
	
	public AbstractKeyboardInputHandler(Component component, Key... keys) {
		this.component = component;
		this.keys = keys;
		this.keyAdapter = new CustomKeyListener();
	}
	
	public AbstractKeyboardInputHandler(Component component, int... keys) {
		this.component = component;
		this.keyAdapter = new CustomKeyListener();
		this.keys = new Key[keys.length];
		for (int i = 0; i < keys.length; i++) {
			this.keys[i] = new Key(keys[i]);
		}
		
	}

	private boolean isKeyMatch(int key, int modifiers) {
		for (int i = 0; i < this.keys.length; i++) {
			if (this.keys[i].key == key && this.keys[i].modifiers == modifiers) 
				return true;
		}
		return false;
	}
	
	/**
	 * Custom key adapter to listen for key events.
	 * @author William Bittle
	 * @version 3.2.1
	 * @since 3.2.0
	 */
	private class CustomKeyListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.isConsumed()) return;
//			System.out.println(e.getKeyChar() + " " + e.getKeyCode() + " " + e.getModifiersEx());
			if (isKeyMatch(e.getKeyCode(), e.getModifiersEx())) {
				if (isEnabled() && !isDependentBehaviorActive()) {
					onKeyPressed();
				}
			}
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			if (e.isConsumed()) return;
			
			if (isKeyMatch(e.getKeyCode(), e.getModifiersEx())) {
				if (isEnabled() && !isDependentBehaviorActive()) {
					onKeyReleased();
				}
			}
		}
	}
	
	@Override
	public void install() {
		this.component.addKeyListener(this.keyAdapter);
	}
	
	@Override
	public void uninstall() {
		this.component.removeKeyListener(this.keyAdapter);
	}
	
	protected void onKeyPressed() {
		
	}
	
	protected void onKeyReleased() {
		
	}
}