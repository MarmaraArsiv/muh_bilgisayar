//bird CPU
module bird (
        input clk,
        input [15:0] data_in,
        output logic [15:0] data_out,
        output logic [11:0] address,
        output memwt
        );

logic [11:0] pc, ir; //program counter, instruction register

logic [4:0] state; //FSM
logic [15:0] regbank [7:0];//registers 
logic zeroflag; //zero flag register
logic [15:0] result; //output for result
 
 
localparam    
		  FETCH=4'b0000,
        LDI=4'b0001, 
        LD=4'b0010,
        ST=4'b0011,
        JZ=4'b0100,
        JMP=4'b0101,
        ALU=4'b0111,
        PUSH=4'b1000,
        POP1=4'b1001,
        POP2=4'b1100,
        CALL=4'b1010,
        RET1=4'b1011,
        RET2=4'b1101;
 

logic zeroresult; 

always_ff @(posedge clk)
    case(state) 
        FETCH: 
            begin
                if ( data_in[15:12]==JZ) // if instruction is jz  
                    if (zeroflag)  //and if last bit of 7th register is 0 then jump to jump instruction state
                        state <= JMP;
                    else
                        state <= FETCH; //stay here to catch next instruction
                else
                    state <= data_in[15:12]; //read instruction opcode and jump the state of the instruction to be read
                ir<=data_in[11:0]; //read instruction details into instruction register
                pc<=pc+1; //increment program counter
            end

        LDI:
            begin
                regbank[ ir[2:0] ] <= data_in; //if inst is LDI get the destination register number from ir and move the data in it.
                pc<=pc+1; //for next instruction (32 bit instruction)  
                state <= FETCH;
            end

        LD:
            begin
                regbank[ir[2:0]] <= data_in;
                state <= FETCH;  
                end 
 
        ST:
            begin
                state <= FETCH;  
            end
 
        JMP:
            begin
                pc <= pc+ir;
                state <= FETCH;  
            end
 
        ALU:
            begin
                regbank[ir[2:0]]<=result;
                zeroflag<=zeroresult;
                state <= FETCH;
            end

        PUSH:
            begin
                 regbank[7]<=regbank[7]-1;
                 state <= FETCH;
            end

        POP1:
            begin
                regbank[7]<=regbank[7]+1;
                state <= POP2;
            end

        POP2: //actually unnecessary
            begin
                regbank[ir[2:0]] <= data_in;
                state <= FETCH; 
            end

        CALL: 
            begin
                regbank[7]<=regbank[7]-1;
                pc<=pc+ir;
                state <= FETCH; 
            end

        RET1:
            begin
                regbank[7]<=regbank[7]+1;
                state <= RET2;
            end

        RET2:
            begin
                pc<=data_in[11:0];
                state <= FETCH; 
            end

    endcase

always_comb
    case (state)
        LD:    address=regbank[ir[5:3]][11:0];
        ST:    address=regbank[ir[5:3]][11:0];
        PUSH:    address=regbank[7];
        POP2:    address=regbank[7];
        CALL:    address=regbank[7];
        RET2:    address=regbank[7];
        default: address=pc;
    endcase
 
 
assign memwt=(state==ST)||(state==PUSH)||(state==CALL);
 
always_comb
    case (state)
        CALL: data_out = {4'b0,pc};
		  ST: data_out = regbank[ir[8:6]];
        default: data_out = regbank[ir[8:6]];
    endcase

always_comb //ALU Operation
    case (ir[11:9])
        3'h0: result = regbank[ir[8:6]]+regbank[ir[5:3]]; //000
        3'h1: result = regbank[ir[8:6]]-regbank[ir[5:3]]; //001
        3'h2: result = regbank[ir[8:6]]&regbank[ir[5:3]]; //010
        3'h3: result = regbank[ir[8:6]]|regbank[ir[5:3]]; //011
        3'h4: result = regbank[ir[8:6]]^regbank[ir[5:3]]; //100
        3'h7: case (ir[8:6])
            3'h0: result = ~regbank[ir[5:3]];
            3'h1: result = regbank[ir[5:3]];
            3'h2: result = regbank[ir[5:3]]+1;
            3'h3: result = regbank[ir[5:3]]-1;
            default: result=16'h0000;
             endcase
        default: result=16'h0000;
    endcase
 
assign zeroresult = ~|result;

initial 
begin
    state=FETCH;
    zeroflag=0;
    pc=0;
end

endmodule