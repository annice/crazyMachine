package com.sample;


public class CrazyMachines {

  private String modelName;
	private int memoryGB;//int as assumed to be in GB
	private int cpuSpeedKHZ;// int as assumed to be KHZ
	private VideoCard videoCardType;
	private float price$;
	private boolean isLoud;
	private boolean isExpensive;
	private boolean isCheap;
	private boolean isHot;
	private boolean canGame;
	private boolean isRisky;
	private boolean canCrunchNos;
	
	public CrazyMachines(String modelName,int memoryGB,int cpuSpeedKHZ,VideoCard videoCardType,float price$){
	this.modelName=modelName;
	this.memoryGB=memoryGB;
	this.cpuSpeedKHZ=cpuSpeedKHZ;
	this.videoCardType=videoCardType;
	this.price$=price$;
	this.isLoud=false;
	this.isExpensive=false;
	this.isHot=false;
	this.isCheap=false;
	this.isRisky=false;
	this.canGame=false;
	}
	
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public int getMemoryGB() {
		return memoryGB;
	}
	public void setMemoryGB(int memoryGB) {
		this.memoryGB = memoryGB;
	}
	public int getCpuSpeedKHZ() {
		return cpuSpeedKHZ;
	}
	public void setCpuSpeedKHZ(int cpuSpeedKHZ) {
		this.cpuSpeedKHZ = cpuSpeedKHZ;
	}
	public VideoCard getVideoCardType() {
		return videoCardType;
	}
	public void setVideoCardType(VideoCard videoCardType) {
		this.videoCardType = videoCardType;
	}
	public float getPrice$() {
		return price$;
	}
	public void setPrice$(float price$) {
		this.price$ = price$;
	}
	public boolean isLoud() {
		return isLoud;
	}
	public void setLoud(boolean isLoud) {
		this.isLoud = isLoud;
	}
	public boolean isExpensive() {
		return isExpensive;
	}
	public void setExpensive(boolean isExpensive) {
		this.isExpensive = isExpensive;
	}
	
	public boolean isCheap() {
		return isCheap;
	}

	public void setCheap(boolean isCheap) {
		this.isCheap = isCheap;
	}

	public boolean isHot() {
		return isHot;
	}
	public void setHot(boolean isHot) {
		this.isHot = isHot;
	}
	public boolean isCanGame() {
		return canGame;
	}
	public void setCanGame(boolean canGame) {
		this.canGame = canGame;
	}
	public boolean isRisky() {
		return isRisky;
	}
	public void setRisky(boolean isRisky) {
		this.isRisky = isRisky;
	}
	public boolean isCanCrunchNos() {
		return canCrunchNos;
	}
	public void setCanCrunchNos(boolean canCrunchNos) {
		this.canCrunchNos = canCrunchNos;
	}
	
	
	
	
}
