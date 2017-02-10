package soccerBet.project.contracts;

public class RunnerContract {

	private double back;
	private double lay;
	private int selectionId;
	
	
	public RunnerContract(Double back, Double lay, int selectionId) {
		// TODO Auto-generated constructor stub
	
		this.back = back;
		this.lay =	lay;
		this.setSelectionId(selectionId);
	
	}
	
	
	
	public void setBack(double back) {
		this.back = back;
	}
	public double getBack() {
		return back;
	}
	public void setLay(double lay) {
		this.lay = lay;
	}
	public double getLay() {
		return lay;
	}



	public void setSelectionId(int selectionId) {
		this.selectionId = selectionId;
	}



	public int getSelectionId() {
		return selectionId;
	}
	
}
