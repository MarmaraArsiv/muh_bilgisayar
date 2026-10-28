#include "stm32l476xx.h"

// PA.5  <--> Green LED
// PC.13 <--> Blue user button
#define LED_PIN 5
#define BUTTON_PIN 13
#define _IO volatile // allows both read and write

volatile int32_t TimeDelay;


volatile uint8_t pressed = 0;




void SysTick_Init(uint32_t ticks) {
  // Disable SysTick
  SysTick -> CTRL = 0;
  // Set the reload register
  SysTick -> LOAD = ticks - 1;
  // Set priority of SysTick to least urgency
  NVIC_SetPriority(SysTick_IRQn, (1 << __NVIC_PRIO_BITS) - 1);
  // Reset the SysTick counter value
  SysTick -> VAL = 0;
  // Select the processor clock
  SysTick -> CTRL |= SysTick_CTRL_CLKSOURCE_Msk;
  // Enables SysTick exception request
  SysTick -> CTRL |= SysTick_CTRL_TICKINT_Msk;
  // Enable SysTick timer
  SysTick -> CTRL |= SysTick_CTRL_ENABLE_Msk;
  // Enable Interrupt here
}

void SysTick_Handler(void) {
  if (TimeDelay > 0) {
    TimeDelay--;
  }
}

void Delay(void) {
  // Busy wait
  while (TimeDelay != 0);
}

void LED_Init(void) {
  // Enable the clock to GPIO Port A	
  RCC -> AHB2ENR |= RCC_AHB2ENR_GPIOAEN;

  // Clear mode bits
  GPIOA -> MODER &= ~(3UL << (2 * LED_PIN));
  // Set mode to output (01)
  GPIOA -> MODER |= 1UL << (2 * LED_PIN);

  // Clear speed bits = set to slow (00)
  GPIOA -> OSPEEDR &= ~(3 << (2 * LED_PIN));

  // Select push-pull output
  GPIOA -> OTYPER &= ~(1 << LED_PIN);

  // No pull-up, no pull-down
  GPIOA -> PUPDR &= ~(3 << (2 * LED_PIN));
}

void LED_Toggle(void) {
  GPIOA -> ODR ^= (1 << LED_PIN);
}

void EXTI_Init(void) {
  // Enable SYSCFG clock
  RCC -> APB2ENR |= RCC_APB2ENR_SYSCFGEN;
  // Enable GPIO clock

  // Select PC.13 as the trigger source of EXTI 13
  SYSCFG -> EXTICR[0] &= -SYSCFG_EXTICR4_EXTI13;
  SYSCFG -> EXTICR[0] |= SYSCFG_EXTICR4_EXTI13_PC;

  // Enable rising edge trigger for EXTI 13
  // Rising trigger selection register (RSTR)
  // 0 = disabled, 1 = enabled
  EXTI -> RTSR1 |= EXTI_RTSR1_RT13;

  // Disable falling edge trigger for EXTI 13
  // Falling trigger selection register (FSTR)
  // 0 = disabled, 1 = enabled
  EXTI -> FTSR1 &= ~EXTI_FTSR1_FT13;

  // Enable EXTI 13 interrupt
  // Interrupt mask register: 0 = disabled, 1 = enabled
  EXTI -> IMR1 |= EXTI_IMR1_IM13;

  // Set EXTI 13 priority to 1
  NVIC_SetPriority(EXTI15_10_IRQn, 1);

  // Enable EXTI 13 interrupt
  NVIC_EnableIRQ(EXTI15_10_IRQn);

  // Clear mode bits = (00) => set mode to input
  GPIOC -> MODER &= ~(3UL << (2 * BUTTON_PIN));

  // No pull-up, no pull-down
  GPIOC -> PUPDR &= ~(3UL << (2 * BUTTON_PIN));
}

void EXTI15_10_IRQHandler(void) {
  // Check for EXTI 13 interrupt flag
  if ((EXTI -> PR1 & EXTI_PR1_PIF13) == EXTI_PR1_PIF13) {
    

    if (pressed == 1) {
      // TODO: Switch between delays
      
    }
    // Clear pressed flag
    pressed = 0;
    // Clear interrupt pending request
    EXTI -> PR1 |= EXTI_PR1_PIF13;
  }
}

int main(void) {
  SysTick_Init(1000);
  LED_Init();
  EXTI_Init();
  // Implement an infinite loop to toggle the green LED in every 0.5 or 1.0 seconds.
  while (1) {
    Delay();
    LED_Toggle();
  }
}