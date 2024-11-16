package software.kosiv.pizzaflow.service;

public enum CustomerGenerationStrategy {
    FAST(10),
    MEDIUM(20),
    SLOW(30);
    
    private final int generationFrequencyInSeconds;
    
    CustomerGenerationStrategy(int generationFrequencyInSeconds) {
        this.generationFrequencyInSeconds = generationFrequencyInSeconds;
    }
    
    public int getGenerationFrequencyInSeconds() {
        return generationFrequencyInSeconds;
    }
}
