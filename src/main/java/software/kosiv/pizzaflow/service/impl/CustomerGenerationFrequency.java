package software.kosiv.pizzaflow.service.impl;

public enum CustomerGenerationFrequency {
    FAST(10),
    MEDIUM(20),
    SLOW(30);
    
    private final int generationFrequencyInSeconds;
    
    CustomerGenerationFrequency(int generationFrequencyInSeconds) {
        this.generationFrequencyInSeconds = generationFrequencyInSeconds;
    }
    
    public int getGenerationFrequencyInSeconds() {
        return generationFrequencyInSeconds;
    }
}
