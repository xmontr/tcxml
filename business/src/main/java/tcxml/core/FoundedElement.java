package tcxml.core;

import java.util.Collection;
import java.util.Collections;
import java.util.Stack;

import org.openqa.selenium.WebElement;

public class FoundedElement {
	
	
	
	private Stack<WebElement> frames;
	
	private WebElement element;

	public FoundedElement(Stack<WebElement> frames, WebElement element) {
		super();
		reverseStack(frames);
		this.frames = frames;
		this.element = element;
	}

	public FoundedElement(WebElement element) {
		super();
		this.element = element;
		this.frames = new Stack<WebElement>();
	}

	public Stack<WebElement> getFrames() {
		

		
		return frames;
	}

	public WebElement getElement() {
		return element;
	}
	
	
	
	 public static <T> void reverseStack(Stack<T> stack) {
	        if (stack.isEmpty()) {
	            return;
	        }
	        // Remove bottom element from stack
	        T bottom = popBottom(stack);
	        
	        // Reverse everything else in stack
	        reverseStack(stack);
	        
	        // Add original bottom element to top of stack
	        stack.push(bottom);
	    }
	    private static <T> T popBottom(Stack<T> stack) {
	        T top = stack.pop();
	        if (stack.isEmpty()) {
	            // If we removed the last element, return it
	            return top;
	        } else {
	            // We didn't remove the last element, so remove the last element from what remains
	            T bottom = popBottom(stack);
	            // Since the element we removed in this function call isn't the bottom element, 
	            // add it back onto the top of the stack where it came from
	            stack.push(top);
	            return bottom;
	        }
	    }
	
	
	

}
